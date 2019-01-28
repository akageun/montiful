package kr.geun.oss.montiful.app.program.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * Program DTO
 *
 * @author akageun
 */
public class ProgramDTO {

    @Data
    @NoArgsConstructor
    public static class PageReq {
        @Min(0)
        private int pageNumber;
    }

    @Data
    @NoArgsConstructor
    public static class PageRes {

    }

    @Data
    @NoArgsConstructor
    public static class GetReq {
        @Min(0)
        private Long programIdx;
    }
}
