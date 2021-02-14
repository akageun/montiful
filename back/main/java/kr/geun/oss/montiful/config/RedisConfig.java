package kr.geun.oss.montiful.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.core.redis.cd.RedisTopicCd;
import kr.geun.oss.montiful.core.redis.publisher.RedisPublisher;
import kr.geun.oss.montiful.core.redis.subscriber.AlarmSubscriber;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis Configuration
 *
 * @author akageun
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase;

    /**
     * Redis Pool Setting
     *
     * @return
     */
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        return genericObjectPoolConfig;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {
        //public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        //r.setPassword(RedisPassword.of(password));

        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                //.commandTimeout(Duration.ofMillis(6000))
                .poolConfig(genericObjectPoolConfig).build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }

    /**
     * redis Template
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return getTemplate(redisConnectionFactory, Object.class);
    }

    /**
     * redis Template
     * - checkResRedisTemplate
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, MonitorDTO.CheckRes> checkResRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return getTemplate(redisConnectionFactory, MonitorDTO.CheckRes.class);
    }

    /**
     * redis Template
     * - URL Entity
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, MonitorDTO.CheckReq> checkReqRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return getTemplate(redisConnectionFactory, MonitorDTO.CheckReq.class);
    }

    private <T> RedisTemplate<String, T> getTemplate(RedisConnectionFactory redisConnectionFactory, Class<T> clz) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(stringSerializer);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);

        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(clz);
        jsonRedisSerializer.setObjectMapper(new ObjectMapper());
        redisTemplate.setValueSerializer(jsonRedisSerializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


    @Bean
    public MessageListenerAdapter alarmListener(AlarmSubscriber alarmSubscriber) {
        return new MessageListenerAdapter(alarmSubscriber);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter alarmListener, LettuceConnectionFactory redisConnectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(alarmListener, new ChannelTopic(RedisTopicCd.NOTIFY.name()));
        return container;
    }

    @Bean
    public RedisPublisher getRedisPublisher() {
        return new RedisPublisher();
    }

    @Bean
    public AlarmSubscriber getAlarmSubscriber() {
        return new AlarmSubscriber();
    }
}
