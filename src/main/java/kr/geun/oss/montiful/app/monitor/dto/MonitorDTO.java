package kr.geun.oss.montiful.app.monitor.dto;

import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    public static class CheckReq {
        private Long urlIdx;

        private String url;
        private String urlName;

        private int connectionTimeout;  //1000 - 10000
        private int readTimeout; //1000 - 10000
        private String healthStatusCd; //HEALTH, WARNING, ERROR
        private String method; //GET, POST
        private String statusCheckTypeCd; //status_200, status-2xx, same_string
        private String statusCheckValue;

    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckRes {
        private Long urlIdx;
        private String urlName;

        private HealthStatusCd healthStatusCd;
        private String resultMsg;
        private Long responseTime;
        private Long runtime;

        private String preHealthStatusCheckCd;
    }

}
