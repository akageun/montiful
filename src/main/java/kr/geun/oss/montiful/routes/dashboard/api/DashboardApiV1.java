package kr.geun.oss.montiful.routes.dashboard.api;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.monitor.service.MonitorHistService;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dashboard Api Controller V1
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/dashboard/api/v1")
public class DashboardApiV1 {

	@Autowired
	private MonitorHistService monitorHistService;

	/**
	 * URL Monitoring History
	 *
	 * @return
	 */
	@GetMapping("/monitor/hist")
	public ResponseEntity<Res> getUrlMonitorHist() {

		Map<String, List<MonitorDTO.CheckRes>> rtnMap = monitorHistService.getList().stream().collect(
			Collectors.groupingBy(MonitorDTO.CheckRes::getUrlName));

		return ResponseEntity.ok(Res.of(true, "SUCCESS", rtnMap));
	}
}
