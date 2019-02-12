package kr.geun.oss.montiful.app.monitor.service.impl;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.monitor.service.AsyncMonitorService;
import kr.geun.oss.montiful.app.monitor.service.MonitorService;
import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Service
public class MonitorServiceImpl implements MonitorService {

	@Autowired
	private UrlService urlService;

	@Autowired
	private RedisTemplate<String, MonitorDTO.CheckReq> checkReqRedisTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private AsyncMonitorService asyncMonitorService;

	private final String URL_ALL_KEY = "program:url:allList";

	@Autowired
	private SysConfService sysConfService;

	@Override
	public void run() {
		if (initHealthCheckList() == false) {
			log.error("에러 알림"); //TODO : Redis publish로 변경?
			return;
		}

		int confValue = Integer.parseInt(sysConfService.getValue(SysConfCd.URL_HEALTH_CHECK_RUN_THREAD));

		Long runTime = new Date().getTime();

		Long urlCheckIdCnt = redisTemplate.opsForList().size(Const.Redis.URL_CHECK_ID);
		urlCheckIdCnt = urlCheckIdCnt == null ? 0 : urlCheckIdCnt;

		if (urlCheckIdCnt >= Const.Redis.MAX_URL_CHECK_CNT) {
			redisTemplate.opsForList().rightPop(Const.Redis.URL_CHECK_ID); //하나를 빼고
		}

		redisTemplate.opsForList().leftPush(Const.Redis.URL_CHECK_ID, Const.Redis.URL_HIST_PREFIX + runTime);

		for (int i = 0; i < confValue; i++) {
			asyncMonitorService.asyncMonitorCheck(runTime, URL_ALL_KEY);
		}
	}

	/**
	 * HealthCheck List Initialize
	 *
	 * @return
	 */
	private boolean initHealthCheckList() {
		checkReqRedisTemplate.delete(URL_ALL_KEY);
		checkReqRedisTemplate.opsForList().leftPushAll(URL_ALL_KEY, urlService.getHealthCheckTargetList());

		return true;
	}
}
