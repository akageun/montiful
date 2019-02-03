package kr.geun.oss.montiful.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author 김형근
 */
public class RedisUtils {

	public static List<String> getRedisKeyList(RedisConnection redisConnection) {
		return getRedisKeyList(redisConnection, null);
	}

	public static List<String> getRedisKeyList(RedisConnection redisConnection, String keyPrefix) {
		keyPrefix = StringUtils.defaultString(keyPrefix, "");

		Set<byte[]> redisKeys = redisConnection.keys((keyPrefix + "*").getBytes());
		List<String> keysList = new ArrayList<>();

		if (redisKeys == null || redisKeys.isEmpty()) {
			return new ArrayList<>();
		}

		for (byte[] data : redisKeys) {
			keysList.add(new String(data, 0, data.length));
		}

		return keysList;
	}
}
