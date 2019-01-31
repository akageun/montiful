package kr.geun.oss.montiful.app.url.service;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * URL Service
 *
 * @author akageun
 */
public interface UrlService {

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	Page<UrlEntity> page(Pageable pageable);

	/**
	 * GetReq
	 *
	 * @param urlIdx
	 * @return
	 */
	Optional<UrlEntity> get(Long urlIdx);

	/**
	 * Add
	 *
	 * @param param
	 * @param alarmIdxs
	 */
	UrlEntity add(UrlEntity param, List<Long> alarmIdxs);

	/**
	 * modify
	 *
	 * @param param
	 * @param alarmIdxs
	 */
	UrlEntity modify(UrlEntity param, List<Long> alarmIdxs);

	/**
	 * Url Name Search
	 *
	 * @param keyword
	 * @return
	 */
	List<UrlEntity> urlNameSearch(String keyword);

	/**
	 * Url Program
	 *
	 * @param programIdx
	 * @return
	 */
	List<UrlEntity> urlProgramList(Long programIdx);

	/**
	 * Url Mapping Alarm List
	 *
	 * @param urlIdx
	 * @return
	 */
	List<AlarmEntity> urlAlarmList(Long urlIdx);

	/**
	 * Health Check
	 *
	 * @param param
	 * @return
	 */
	Optional<MonitorDTO.CheckRes> healthCheck(MonitorDTO.CheckReq param);

	List<MonitorDTO.CheckReq> getHealthCheckTargetList();

	/**
	 * Status Value Modify
	 *
	 * @param list
	 */
	void modifyHealthStatusCheck(List<MonitorDTO.CheckRes> list);
}
