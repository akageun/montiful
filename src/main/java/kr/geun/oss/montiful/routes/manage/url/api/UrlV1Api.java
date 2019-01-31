package kr.geun.oss.montiful.routes.manage.url.api;

import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.response.Res;
import kr.geun.oss.montiful.core.utils.SecUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/manage/url/api/v1")
public class UrlV1Api {

	@Autowired
	private UrlService urlService;

	@GetMapping(value = "/{urlIdx}")
	public ResponseEntity<Res> getUrl(@Valid UrlDTO.GetReq param, BindingResult result) {
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

	@PostMapping(value = "")
	public ResponseEntity<UrlEntity> addUrl(@Valid UrlDTO.Add param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UrlEntity());
		}

		//TODO : 중복된 이름 검색

		String userId = SecUtils.userId();

		//@formatter:off
		UrlEntity dbInfo = UrlEntity.builder()
				.urlName(param.getUrlName())
                .url(param.getUrl())
				.memo(param.getMemo())
                .healthStatusCd(HealthStatusCd.HEALTH.name())
                .connectionTimeout(param.getConnectionTimeout())
                .readTimeout(param.getReadTimeout())
                .statusCheckTypeCd(param.getStatusCheckTypeCd())
                .statusCheckValue(param.getStatusCheckValue())
                .method(param.getMethod())

				.createdUserId(userId)
				.updatedUserId(userId)
			.build();
		//@formatter:on

		urlService.add(dbInfo, param.getAlarmIdxs());

		return ResponseEntity.status(HttpStatus.CREATED).body(new UrlEntity());
	}

	@PutMapping(value = "")
	public ResponseEntity<UrlEntity> modifyUrl(@RequestBody @Valid UrlDTO.Modify param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UrlEntity());
		}

		String userId = SecUtils.userId();

		//@formatter:off
		UrlEntity dbInfo = UrlEntity.builder()
                .urlIdx(param.getUrlIdx())
				.urlName(param.getUrlName())
                .url(param.getUrl())
				.memo(param.getMemo())
				.healthStatusCd(param.getHealthStatusCd())
                .connectionTimeout(param.getConnectionTimeout())
                .readTimeout(param.getReadTimeout())
                .statusCheckTypeCd(param.getStatusCheckTypeCd())
                .statusCheckValue(param.getStatusCheckValue())
                .method(param.getMethod())

				.updatedUserId(userId)
			.build();
		//@formatter:on

		urlService.modify(dbInfo, param.getAlarmIdxs());

		return ResponseEntity.status(HttpStatus.OK).body(new UrlEntity());
	}
}
