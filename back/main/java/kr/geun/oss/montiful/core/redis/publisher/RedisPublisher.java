package kr.geun.oss.montiful.core.redis.publisher;

import kr.geun.oss.montiful.core.redis.cd.RedisTopicCd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * @author akageun
 */
@Slf4j
public class RedisPublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Publish
     *
     * @param topicCd
     * @param obj
     */
    public void publish(RedisTopicCd topicCd, Object obj) {
        String strTopic = new ChannelTopic(topicCd.name()).getTopic();

        if (RedisTopicCd.NOTIFY.equals(topicCd)) {
            redisTemplate.convertAndSend(strTopic, obj);
        } else {
            throw new IllegalArgumentException("Not Supported Topic Code!!");
        }
    }
}
