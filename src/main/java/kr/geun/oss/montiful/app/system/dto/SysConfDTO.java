package kr.geun.oss.montiful.app.system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * System Configuration DTO
 *
 * @author akageun
 */
public class SysConfDTO {

    @Data
    @NoArgsConstructor
    public static class PageReq {

        @Min(0)
        private int pageNumber;
    }
}
