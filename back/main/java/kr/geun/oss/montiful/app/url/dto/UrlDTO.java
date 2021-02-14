package kr.geun.oss.montiful.app.url.dto;

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
    @NoArgsConstructor
    public static class StatusCnt {

        private Long urlCnt;
        private String healthStatusCd;
    }
}
