package kr.geun.oss.montiful.app.url.service.impl;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.service.UrlHistService;
import kr.geun.oss.montiful.core.constants.Const;
import kr.geun.oss.montiful.core.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
public class UrlHistServiceImpl implements UrlHistService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private RedisTemplate<String, MonitorDTO.CheckRes> checkResRedisTemplate;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public List<String> getUrlHistList() {
		//return checkResRedisTemplate.opsForZSet().range(Const.Redis.URL_HIST_PREFIX + "*");

		return RedisUtils.getRedisKeyList(redisConnectionFactory.getConnection(), Const.Redis.URL_HIST_PREFIX);
	}

	@Override
	public void urlAppendHealthCheckHist(Long runTime, List<MonitorDTO.CheckRes> allList) {
		String redisKey = Const.Redis.URL_HIST_PREFIX + runTime;

		Map<Long, List<MonitorDTO.CheckRes>> l = allList.stream().collect(Collectors.groupingBy(MonitorDTO.CheckRes::getUrlIdx));
		l.forEach((key, value) -> value.forEach(i -> checkResRedisTemplate.opsForZSet().add(redisKey, i, i.getRuntime())));

		final int ttl = 1; //TODO SysConfig로 변경해야함.
		checkResRedisTemplate.expire(redisKey, ttl, TimeUnit.HOURS);
	}
}