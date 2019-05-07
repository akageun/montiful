package kr.geun.oss.montiful.routes.system.sysConf.api;

import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * config 관리 API V1
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/manage/configuration/api/v1")
public class SysConfV1Api {

    @Autowired
    private SysConfService sysConfService;

    /**
     * 단건조회
     *
     * @param confCd
     * @return
     */
    @GetMapping("/{confCd}")
    public ResponseEntity<Res> get(
            @PathVariable String confCd
    ) {
        if (StringUtils.isBlank(confCd)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
        }

        Optional<SysConfEntity> urlEntity = sysConfService.get(confCd);
        if (urlEntity.isPresent() == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Res.of(false, "데이터가 없습니다."));
        }

        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("result", urlEntity.get());

        return ResponseEntity.ok().body(Res.of(true, "SUCCESS", rtnMap));
    }
}
