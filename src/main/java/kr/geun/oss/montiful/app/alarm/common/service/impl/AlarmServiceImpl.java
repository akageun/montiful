package kr.geun.oss.montiful.app.alarm.common.service.impl;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.repo.AlarmRepo;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}
