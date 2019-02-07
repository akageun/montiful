package kr.geun.oss.montiful.routes.monitor.url.api;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.monitor.service.MonitorHistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/hist")
    public ResponseEntity<Map> getUrlHist() {
        return ResponseEntity.ok().body(monitorHistService.getList().stream().collect(Collectors.groupingBy(MonitorDTO.CheckRes::getUrlName)));
    }

}
