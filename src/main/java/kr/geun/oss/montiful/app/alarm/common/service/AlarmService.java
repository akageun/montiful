package kr.geun.oss.montiful.app.alarm.common.service;

import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.cd.AlarmManageSearchTypeCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.repo.AlarmRepo;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import kr.geun.oss.montiful.app.url.repo.UrlAlarmRepo;
import kr.geun.oss.montiful.core.redis.cd.RedisTopicCd;
import kr.geun.oss.montiful.core.redis.publisher.RedisPublisher;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Alarm Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class AlarmService {

    @Autowired
    private AlarmRepo alarmRepo;

    @Autowired
    private UrlAlarmRepo urlAlarmRepo;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisPublisher redisPublisher;

    @Autowired
    private SysConfService sysConfService;

    /**
     * 리스트 페이지 조회
     *
     * @param pageable
     * @param searchType  : 검색타입
     * @param searchValue : 검색 값
     * @return
     */
    public Page<AlarmEntity> page(Pageable pageable, String searchType, String searchValue) {
        AlarmManageSearchTypeCd searchTypeCd = EnumUtils.getEnum(AlarmManageSearchTypeCd.class, searchType);

        if (CmnUtils.isSearchable(searchTypeCd, searchValue)) { //검색조건이 있을 경우
            return alarmRepo.findPage(pageable, searchTypeCd, searchValue);
        }

        return alarmRepo.findAll(pageable);
    }

    /**
     * 단건조회
     *
     * @param alarmIdx
     * @return
     */
    public Optional<AlarmEntity> get(Long alarmIdx) {
        return alarmRepo.findById(alarmIdx);
    }

    /**
     * 알람 서비스 검색
     *
     * @param keyword
     * @return
     */
    public List<AlarmEntity> search(String keyword) {
        List<AlarmEntity> searchList = alarmRepo.findByAlarmNameStartingWith(keyword);

        if (CollectionUtils.isEmpty(searchList)) {
            return Collections.emptyList();
        }

        return searchList;
    }

    /**
     * 알람 추가.
     *
     * @param param
     * @return
     */
    public AlarmEntity add(AlarmEntity param) {
        return alarmRepo.save(param);
    }

    /**
     * 알람 수정
     *
     * @param param
     * @return
     */
    public AlarmEntity modify(AlarmEntity param) {
        return alarmRepo.save(param);
    }

    /**
     * Alarm Register
     *
     * @param list
     */
    public void alarmPublisher(List<MonitorDTO.CheckRes> list) {
        if (list.isEmpty()) {
            return;
        }

        String confValue = sysConfService.getValue(SysConfCd.GLOBAL_ALARM_YN);
        if ("N".equals(confValue)) {
            return;
        }

        for (MonitorDTO.CheckRes info : list) {
            redisPublisher.publish(RedisTopicCd.NOTIFY, info);
        }

    }

    /**
     * Send Alarm
     *
     * @param checkRes
     */
    public void sendAlarm(MonitorDTO.CheckRes checkRes) {
        List<AlarmEntity> notifyEntities = urlAlarmRepo.findUrlAlarmListByUrlIdx(checkRes.getUrlIdx());
        if (notifyEntities.isEmpty()) {
            log.debug("등록된 알람이 없습니다.");
            return;
        }

        for (AlarmEntity info : notifyEntities) {
            AlarmChannelCd notificationChannelCd = EnumUtils.getEnum(AlarmChannelCd.class, info.getAlarmChannel());
            if (notificationChannelCd == null) {
                log.error("등록되지 않은 알람 발송 : {}, object: {}", info.getAlarmChannel(), info);
                return;
            }

            IAlarmChannelService notificationChannelService = (IAlarmChannelService) applicationContext.getBean(notificationChannelCd.getBeanName());
            notificationChannelService.asyncSendMsg(checkRes, info.getAlarmValue());
        }

    }
}
