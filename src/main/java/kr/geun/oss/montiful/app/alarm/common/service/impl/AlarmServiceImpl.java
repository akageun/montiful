package kr.geun.oss.montiful.app.alarm.common.service.impl;

import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.repo.AlarmRepo;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmChannelService;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.redis.cd.RedisTopicCd;
import kr.geun.oss.montiful.app.redis.publisher.service.RedisPublisher;
import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import kr.geun.oss.montiful.app.url.repo.UrlAlarmRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Alarm Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmRepo alarmRepo;

	@Autowired
	private UrlAlarmRepo urlAlarmRepo;

	@Autowired
	private RedisPublisher redisPublisher;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private SysConfService sysConfService;

	@Override
	public Page<AlarmEntity> page(Pageable pageable) {
		return alarmRepo.findAll(pageable);
	}

	@Override
	public Optional<AlarmEntity> get(Long alarmIdx) {
		return alarmRepo.findById(alarmIdx);
	}

	@Override
	public List<AlarmEntity> search(String keyword) {
		return alarmRepo.findByAlarmNameStartingWith(keyword);
	}

	@Override
	public AlarmEntity add(AlarmEntity param) {
		return alarmRepo.save(param);
	}

	@Override
	public AlarmEntity modify(AlarmEntity param) {
		return alarmRepo.save(param);
	}

	/**
	 * Alarm Register
	 *
	 * @param list
	 */
	@Override
	public void alarmPublisher(List<MonitorDTO.CheckRes> list) {
		if (list.isEmpty()) {
			return;
		}

		String confValue = sysConfService.getValue(SysConfCd.GLOBAL_ALARM_YN);
		if ("N".equals(confValue)) {
			return;
		}

		list.forEach(info -> redisPublisher.publish(RedisTopicCd.NOTIFY, info));
	}

	/**
	 * Send Alarm
	 *
	 * @param checkRes
	 */
	@Override
	public void sendAlarm(MonitorDTO.CheckRes checkRes) {
		List<AlarmEntity> notifyEntities = urlAlarmRepo.findUrlAlarmListByUrlIdx(checkRes.getUrlIdx());
		if (notifyEntities.isEmpty()) {
			log.debug("등록된 알람이 없습니다.");
			return;
		}

		notifyEntities.forEach(info -> {
			AlarmChannelCd notificationChannelCd = EnumUtils.getEnum(AlarmChannelCd.class, info.getAlarmChannel());
			if (notificationChannelCd == null) {
				log.error("등록되지 않은 알람 발송 : {} ", info.getAlarmChannel());
				return;
			}

			AlarmChannelService notificationChannelService = (AlarmChannelService) applicationContext.getBean(notificationChannelCd.getBeanName());
			notificationChannelService.send(checkRes, info);
		});
	}
}
