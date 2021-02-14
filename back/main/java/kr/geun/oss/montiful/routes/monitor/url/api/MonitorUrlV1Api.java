package kr.geun.oss.montiful.routes.monitor.url.api;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.cd.ErrorCd;
import kr.geun.oss.montiful.core.exception.BaseException;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/monitor/url/api/v1")
public class MonitorUrlV1Api {

    @Autowired
    private UrlService urlService;

    @GetMapping(value = "/list")
    public ResponseEntity<Res> getUrlList(
            @Valid MonitorDTO.UrlListReq param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        Map<String, Object> rtnMap = urlService.getUrlInfoListByProgramIdx(param.getProgramIdx());
        return ResponseEntity.ok().body(Res.of(true, "SUCCESS", rtnMap));
    }
}
