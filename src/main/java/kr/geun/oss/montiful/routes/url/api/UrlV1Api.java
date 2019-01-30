package kr.geun.oss.montiful.routes.url.api;

import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/url")
public class UrlV1Api {

	@Autowired
	private UrlService urlService;

	@GetMapping(value = "/{urlIdx}")
	public ResponseEntity<Res> getProgram(@Valid UrlDTO.GetReq param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}
		Optional<UrlEntity> urlEntity = urlService.get(param.getUrlIdx());
		//TODO : 방어로직 추가해야함.

		Map<String, Object> rtnMap = new HashMap<>();
		rtnMap.put("result", urlEntity.get());
		rtnMap.put("alarmList", urlService.urlAlarmList(param.getUrlIdx()));

		return ResponseEntity.ok().body(Res.of(true, "SUCCESS", rtnMap));
	}
}
