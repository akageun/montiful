package kr.geun.oss.montiful.app.monitor.service;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.core.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Service
public class MonitorHistService {

	@Autowired
	private RedisTemplate<String, MonitorDTO.CheckRes> checkResRedisTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public void saveMonitorAllHist(Long runTime, List<MonitorDTO.CheckRes> allList) {
		String redisKey = Const.Redis.URL_HIST_PREFIX + runTime;

		Map<Long, List<MonitorDTO.CheckRes>> l = allList.stream().collect(Collectors.groupingBy(MonitorDTO.CheckRes::getUrlIdx));
		l.forEach((key, value) -> value.forEach(i -> checkResRedisTemplate.opsForZSet().add(redisKey, i, i.getRuntime())));

		final int ttl = 60; //TODO SysConfig로 변경해야함.
		checkResRedisTemplate.expire(redisKey, ttl, TimeUnit.MINUTES);
	}

	public List<Object> getUrlHistList() {
		return redisTemplate.opsForList().range(Const.Redis.URL_CHECK_ID, 0, Const.Redis.MAX_URL_CHECK_CNT);
	}

	public List<MonitorDTO.CheckRes> getList() {

		List<Object> keyList = getUrlHistList();
		List<MonitorDTO.CheckRes> checkList = new ArrayList<>();
		keyList.forEach(keyObj -> {
			Set<MonitorDTO.CheckRes> valueList = checkResRedisTemplate.opsForZSet().range(String.valueOf(keyObj), 0, -1);
			if (valueList.isEmpty() == false) {
				checkList.addAll(valueList);
			}

		});

		return checkList;
	}
}
