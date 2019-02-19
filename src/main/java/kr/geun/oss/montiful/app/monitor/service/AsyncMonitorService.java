package kr.geun.oss.montiful.app.monitor.service;

import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import kr.geun.oss.montiful.app.url.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
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
public class AsyncMonitorService {

	@Autowired
	private RedisTemplate<String, MonitorDTO.CheckReq> checkReqRedisTemplate;

	@Autowired
	private UrlService urlService;

	@Autowired
	private AlarmService alarmService;

	@Autowired
	private MonitorHistService monitorHistService;

	/**
	 * Health Check Monitoring
	 *
	 * @param runTime
	 * @param redisKey
	 */
	@Async
	public void asyncMonitorCheck(Long runTime, String redisKey) {
		log.debug("Execute method asynchronously - {}", Thread.currentThread().getName());

		MonitorDTO.CheckReq entity;
		List<MonitorDTO.CheckRes> allList = new ArrayList<>();
		List<MonitorDTO.CheckRes> chgTargetList = new ArrayList<>();
		do {
			Optional<MonitorDTO.CheckRes> checkRes;

			entity = checkReqRedisTemplate.opsForList().rightPop(redisKey);
			if (entity == null) {
				break;

			}
			HealthStatusCd preHealthStatusCd = EnumUtils.getEnum(HealthStatusCd.class, entity.getHealthStatusCd());
			if (preHealthStatusCd == null) {
				checkRes = Optional.of(MonitorDTO.CheckRes.builder().healthStatusCd(HealthStatusCd.WARNING).resultMsg("설정값이 잘못되었습니다.").build());

			} else {

				checkRes = urlService.healthCheck(entity);
				if (checkRes.isPresent() == false) {
					continue;
				}
			}

			MonitorDTO.CheckRes urlEntity = checkRes.get();
			urlEntity.setRuntime(runTime);
			urlEntity.setUrlIdx(entity.getUrlIdx());
			urlEntity.setUrlName(entity.getUrlName());
			urlEntity.setPreHealthStatusCheckCd(preHealthStatusCd);

			if (urlEntity.getHealthStatusCd() != preHealthStatusCd) {
				chgTargetList.add(urlEntity);
			}

			allList.add(urlEntity);

		} while (entity != null);

		urlService.modifyHealthStatusCheck(chgTargetList); //IN Query로 현재 상태 업데이트
		alarmService.alarmPublisher(chgTargetList); //알림용 publisher에 등록
		monitorHistService.saveMonitorAllHist(runTime, allList); //TTL 지정해서 redis에 추가함.
	}
}
