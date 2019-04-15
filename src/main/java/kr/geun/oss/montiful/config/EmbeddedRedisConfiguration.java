package kr.geun.oss.montiful.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

/**
 *
 *
 * @author akageun
 */
@Configuration
@Profile({"h2"})
public class EmbeddedRedisConfiguration implements InitializingBean, DisposableBean {

	@Value("${spring.redis.port}")
	private int redisPort;

	private RedisServer redisServer;

	/**
	 * Redis Stop
	 *
	 */
	@Override
	public void destroy() {
		if (redisServer != null) {
			redisServer.stop(); //Redis Stop
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		redisServer = RedisServer.builder().port(redisPort).setting("maxmemory 128M") //maxheap 128M
			.build();
		redisServer.start(); //Redis Start
	}
}
