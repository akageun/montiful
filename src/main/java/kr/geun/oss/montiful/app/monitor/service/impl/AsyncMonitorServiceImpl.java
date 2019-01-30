package kr.geun.oss.montiful.app.monitor.service.impl;

import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.monitor.service.AsyncMonitorService;
import kr.geun.oss.montiful.app.url.service.UrlHistService;
import kr.geun.oss.montiful.app.url.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Service
public class AsyncMonitorServiceImpl implements AsyncMonitorService {

    @Autowired
    private RedisTemplate<String, MonitorDTO.CheckReq> checkReqRedisTemplate;

    @Autowired
    private UrlService urlService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private UrlHistService urlHistService;

    @Async
    @Override
    public void asyncMonitorCheck(Long runTime, String redisKey) {
        log.debug("Execute method asynchronously - {}", Thread.currentThread().getName());

        MonitorDTO.CheckReq entity;
        List<MonitorDTO.CheckRes> allList = new ArrayList<>();
        List<MonitorDTO.CheckRes> chgTargetList = new ArrayList<>();

        do {
            entity = checkReqRedisTemplate.opsForList().rightPop(redisKey);
            if (entity == null) {
                break;

            }
            String preHealthStatusCheckCd = entity.getHealthStatusCd();

            Optional<MonitorDTO.CheckRes> optionalUrlEntity = urlService.healthCheck(entity);
            if (optionalUrlEntity.isPresent() == false) {
                continue;
            }

            MonitorDTO.CheckRes urlEntity = optionalUrlEntity.get();
            urlEntity.setRuntime(System.currentTimeMillis());
            urlEntity.setUrlIdx(entity.getUrlIdx());
            urlEntity.setUrlName(entity.getUrlName());
            urlEntity.setPreHealthStatusCheckCd(preHealthStatusCheckCd);

            if (StringUtils.equals(urlEntity.getHealthStatusCd().name(), preHealthStatusCheckCd) == false) {
                chgTargetList.add(urlEntity);
            }

            allList.add(urlEntity);

        } while (entity != null);

        urlService.modifyHealthStatusCheck(chgTargetList); //IN Query로 현재 상태 업데이트
        alarmService.alarmRegister(chgTargetList); //알림용 publisher에 등록
        urlHistService.urlAppendHealthCheckHist(runTime, allList); //TTL 지정해서 redis에 추가함.
    }
}
