package kr.geun.oss.montiful.app.redis.subscriber;

import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Alarm Redis Subscriber
 *
 * @author akageun
 */
@Slf4j
@Component
public class AlarmSubscriber implements MessageListener {

    @Autowired
    private AlarmService alarmService;

    /**
     * Callback for processing received objects through Redis.
     *  @param message message must not be {@literal null}.
     * @param pattern pattern matching the channel (if specified) - can be {@literal null}.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();

        try {
            MonitorDTO.CheckRes checkRes = new Jackson2JsonRedisSerializer<>(MonitorDTO.CheckRes.class).deserialize(body);
            log.debug("Message received: :{} / {}", checkRes, Thread.currentThread().getName());

            alarmService.sendAlarm(checkRes);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
