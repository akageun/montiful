package kr.geun.oss.montiful.app.redis.publisher.service;

import kr.geun.oss.montiful.app.redis.cd.RedisTopicCd;

/**
 * Redis Publisher
 *
 * @author akageun
 */
public interface RedisPublisher {

	/**
	 * Publish
	 *
	 * @param topicCd
	 * @param obj
	 */
	void publish(RedisTopicCd topicCd, Object obj);
}
