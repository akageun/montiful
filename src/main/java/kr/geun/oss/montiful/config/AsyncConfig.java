package kr.geun.oss.montiful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Async Configurer
 *
 * @author akageun
 */
@EnableAsync
@Configuration
public class AsyncConfig {

	/**
	 * executor
	 *
	 * @return
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("worker-");
		executor.setCorePoolSize(10); //min
		executor.setMaxPoolSize(100); //max
		executor.setQueueCapacity(100); //queue
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); //큐와 스레드에 가득차면 예외발생하고 무효됨
		return executor;
	}
}
