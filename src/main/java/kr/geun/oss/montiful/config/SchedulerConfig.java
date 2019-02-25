package kr.geun.oss.montiful.config;

import kr.geun.oss.montiful.app.monitor.service.MonitorService;
import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Configuration
@EnableScheduling
public class SchedulerConfig {

	@Autowired
	private SysConfService sysConfService;

	@Autowired
	private MonitorService monitorService;

	//@Scheduled(cron = "0 0/1 * * * ?")
	@Scheduled(cron = "0/15 * * * * ?")
	public void anotherJob() {
		String confValue = sysConfService.getValue(SysConfCd.URL_HEALTH_CHECK_RUN_YN);
		if ("N".equals(confValue)) {
			return;
		}

		//monitorService.run();
	}
}
