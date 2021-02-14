package kr.geun.oss.montiful.app.monitor.dto;

import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import lombok.*;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * Monitor DTO
 *
 * @author akageun
 */
public class MonitorDTO {

    @Data
    @NoArgsConstructor
    public static class PageReq {
        @Min(0)
        private int pageNumber;
    }

    @Data
    @NoArgsConstructor
    public static class ViewerReq {
        @Min(0)
        private Long programIdx;
    }

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

    @Getter
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckRes {
        private Long urlIdx;
        private String urlName;

        private HealthStatusCd healthStatusCd;
        private String resultMsg;

        @Setter
        private Long responseTime;
        private Long runtime;

        private HealthStatusCd preHealthStatusCheckCd;

        public void setHealthAndMsg(HealthStatusCd healthStatusCd, String resultMsg) {
            this.healthStatusCd = healthStatusCd;
            this.resultMsg = resultMsg;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MonitorHistRes {
        private Long monitorHistIdx;
        private Long urlIdx;
        private String urlName;
        private String healthStatusCd;
        private String preHealthStatusCheckCd;

        private LocalDateTime createdAt;
    }

    @Data
    @NoArgsConstructor
    public static class UrlListReq {
        @Min(0)
        private Long programIdx;

    }
}
