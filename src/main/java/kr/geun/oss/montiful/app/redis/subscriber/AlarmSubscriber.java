package kr.geun.oss.montiful.app.redis.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * Alarm Redis Subscriber
 *
 * @author akageun
 */
public class AlarmSubscriber implements MessageListener {

    /**
     * Callback for processing received objects through Redis.
     *  @param message message must not be {@literal null}.
     * @param pattern pattern matching the channel (if specified) - can be {@literal null}.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
