package kr.geun.oss.montiful.routes.monitor.program.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 * @author 김형근
 */
@Slf4j
@Controller
@RequestMapping("/monitor")
public class MonitorProgramWeb {

	@GetMapping("/program")
	public String getMonitorProgram() {
		return "monitor/program/programList";
	}
}
