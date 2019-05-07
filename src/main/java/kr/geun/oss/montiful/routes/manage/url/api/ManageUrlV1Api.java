package kr.geun.oss.montiful.routes.manage.url.api;

import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.response.Res;
import kr.geun.oss.montiful.core.utils.SecUtils;
import kr.geun.oss.montiful.routes.manage.url.dto.ManageUrlDTO;
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
 * URL 관리 API V1
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/manage/url/api/v1")
public class ManageUrlV1Api {

    @Autowired
    private UrlService urlService;

    /**
     * 단건조회
     *
     * @param urlIdx
     * @return
     */
    @GetMapping(value = "/{urlIdx}")
    public ResponseEntity<Res> getUrl(
            @PathVariable Long urlIdx
    ) {

        if (urlIdx == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
        }
        Optional<UrlEntity> urlEntity = urlService.get(urlIdx);
        if (urlEntity.isPresent() == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Res.of(false, "데이터가 없습니다."));
        }

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("result", urlEntity.get());
        rtnMap.put("alarmList", urlService.urlAlarmList(urlIdx));

        return ResponseEntity.ok().body(Res.of(true, "SUCCESS", rtnMap));
    }

    /**
     * 저장
     *
     * @param param
     * @param result
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity<Res> addUrl(
            @Valid ManageUrlDTO.Add param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
        }

        //TODO : 중복된 이름 검색

        try {

            String userId = SecUtils.userId();

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

            urlService.add(dbInfo, param.getAlarmIdxs());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Res.ok());
    }

    /**
     * 수정
     *
     * @param param
     * @param result
     * @return
     */
    @PutMapping(value = "")
    public ResponseEntity<Res> modifyUrl(
            @RequestBody @Valid ManageUrlDTO.Modify param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
        }

        try {
            String userId = SecUtils.userId();

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

            urlService.modify(dbInfo, param.getAlarmIdxs());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


        return ResponseEntity.ok(Res.of(true, "SUCCESS"));
    }
}
