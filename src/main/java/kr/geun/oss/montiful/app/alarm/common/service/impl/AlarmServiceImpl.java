package kr.geun.oss.montiful.app.alarm.common.service.impl;

import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.repo.AlarmRepo;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmChannelService;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.redis.cd.RedisTopicCd;
import kr.geun.oss.montiful.app.redis.publisher.service.RedisPublisher;
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
 *
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
	public void alarmRegister(List<MonitorDTO.CheckRes> list) {
		if (list.isEmpty()) {
			return;
		}

		list.forEach(info -> redisPublisher.publish(RedisTopicCd.NOTIFY, info));
	}

	@Override
	public void sendAlarm(MonitorDTO.CheckRes checkRes) {
		List<AlarmEntity> notifyEntities = urlAlarmRepo.findUrlNotificationListByUrlIdx(checkRes.getUrlIdx());

		notifyEntities.forEach(info -> {
			AlarmChannelCd notificationChannelCd = EnumUtils.getEnum(AlarmChannelCd.class, info.getAlarmChannel());
			if (notificationChannelCd == null) {
				return;
			}

			AlarmChannelService notificationChannelService = (AlarmChannelService)applicationContext.getBean(notificationChannelCd.getBeanName());
			notificationChannelService.send(checkRes, info);
		});
	}
}
