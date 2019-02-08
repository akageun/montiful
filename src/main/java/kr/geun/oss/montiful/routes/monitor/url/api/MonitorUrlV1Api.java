package kr.geun.oss.montiful.routes.monitor.url.api;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.monitor.service.MonitorHistService;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/monitor/url/api/v1")
public class MonitorUrlV1Api {

	@Autowired
	private MonitorHistService monitorHistService;

	@Autowired
	private UrlService urlService;

	@GetMapping(value = "/hist")
	public ResponseEntity<Map> getUrlHist() {
		return ResponseEntity.ok().body(monitorHistService.getList().stream().collect(Collectors.groupingBy(MonitorDTO.CheckRes::getUrlName)));
	}

	@GetMapping(value = "/list")
	public ResponseEntity<Res> getUrlList(@Valid MonitorDTO.UrlListReq param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		Map<String, Object> rtnMap = urlService.getListByProgramIdx(param.getProgramIdx());
		return ResponseEntity.ok().body(Res.of(true, "SUCCESS", rtnMap));
	}
}
