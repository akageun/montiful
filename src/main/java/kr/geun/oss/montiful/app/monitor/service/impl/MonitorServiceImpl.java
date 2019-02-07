package kr.geun.oss.montiful.app.monitor.service.impl;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.monitor.service.AsyncMonitorService;
import kr.geun.oss.montiful.app.monitor.service.MonitorService;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.constants.Const;
import kr.geun.oss.montiful.core.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

	@Override
	public void run() {

		if (initHealthCheckList() == false) {
			log.error("에러 알림"); //TODO : Redis publish로 변경?
			return;
		}

		final int maxRunThread = 3; //TODO : 디비에서 가져오는 형태!

		Long runTime = new Date().getTime();

		Long urlCheckIdCnt = redisTemplate.opsForList().size(Const.Redis.URL_CHECK_ID);
		urlCheckIdCnt = urlCheckIdCnt == null ? 0 : urlCheckIdCnt;

		if (urlCheckIdCnt > 10) {
			redisTemplate.opsForList().rightPop(Const.Redis.URL_CHECK_ID); //하나를 빼고
		}

		redisTemplate.opsForList().leftPush(Const.Redis.URL_CHECK_ID, Const.Redis.URL_HIST_PREFIX + runTime);

		for (int i = 0; i < maxRunThread; i++) {
			asyncMonitorService.asyncMonitorCheck(runTime, URL_ALL_KEY);
		}

		return;
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
