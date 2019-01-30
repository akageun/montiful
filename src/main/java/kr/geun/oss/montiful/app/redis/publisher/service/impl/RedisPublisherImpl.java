package kr.geun.oss.montiful.app.redis.publisher.service.impl;

import kr.geun.oss.montiful.app.redis.cd.RedisTopicCd;
import kr.geun.oss.montiful.app.redis.publisher.service.RedisPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Service
public class RedisPublisherImpl implements RedisPublisher {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * Publish
	 *
	 * @param topicCd
	 * @param obj
	 */
	@Override
	public void publish(RedisTopicCd topicCd, Object obj) {
		String strTopic = new ChannelTopic(topicCd.name()).getTopic();

		if (RedisTopicCd.NOTIFY.equals(topicCd)) {
			redisTemplate.convertAndSend(strTopic, obj);
		} else {
			throw new IllegalArgumentException("Not Supported Topic Code!!");
		}
	}
}
