package kr.geun.oss.montiful.app.monitor.dto;

import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author akageun
 */
public class MonitorDTO {

    @Data
    @NoArgsConstructor
    public static class CheckRes {
        private Long urlIdx;
        private String urlName;

        private HealthStatusCd healthStatusCd;
        private String resultMsg;
        private Long responseTime;
        private Long runtime;
    }

}
