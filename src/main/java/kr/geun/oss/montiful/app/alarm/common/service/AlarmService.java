package kr.geun.oss.montiful.app.alarm.common.service;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Alarm Service Interface
 *
 * @author akageun
 */
public interface AlarmService {

	Page<AlarmEntity> page(Pageable pageable);

	Optional<AlarmEntity> get(Long alarmIdx);

	List<AlarmEntity> search(String keyword);

	AlarmEntity add(AlarmEntity param);

	AlarmEntity modify(AlarmEntity param);

	/**
	 * Alarm Register
	 *
	 * @param list
	 */
	void alarmPublisher(List<MonitorDTO.CheckRes> list);

	void sendAlarm(MonitorDTO.CheckRes checkRes);
}
