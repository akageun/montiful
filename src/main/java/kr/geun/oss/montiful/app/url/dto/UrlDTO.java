package kr.geun.oss.montiful.app.url.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * URL DTO
 *
 * @author akageun
 */
public class UrlDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PageReq extends CmnPageModule {
        private String st; //SearchType
        private String sv; //SearchValue TODO : Create Search Custom Valid Annotation(Length...)
    }


    @Getter
    @Builder
    @AllArgsConstructor
    public static class Add {
        private String urlName;
        private String url;
        private String memo;

        @Min(100)
        @Max(10000)
        private int connectionTimeout;

        @Min(100)
        @Max(10000)
        private int readTimeout;
        private String method;
        private String statusCheckTypeCd;
        private String statusCheckValue;
        //private boolean notify;

        private List<Long> alarmIdxs;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Modify {

        @Min(0)
        private Long urlIdx;
        private String urlName;
        private String url;
        private String memo;

        @Min(100)
        @Max(10000)
        private int connectionTimeout;

        @Min(100)
        @Max(10000)
        private int readTimeout;
        private String healthStatusCd;
        private String method;
        private String statusCheckTypeCd;
        private String statusCheckValue;

        private List<Long> alarmIdxs;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatusCnt {

        private Long urlCnt;
        private String healthStatusCd;
    }
}
