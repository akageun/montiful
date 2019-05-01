package kr.geun.oss.montiful.app.program.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Program DTO
 *
 * @author akageun
 */
public class ProgramDTO {

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
    @NoArgsConstructor
    public static class PageRes {
        private Long programIdx;
        private String programName;
        private String memo;
        private String createdUserId;
        private String updatedUserId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddReq {

        @NotBlank
        @Size(min = 1, max = 100)
        private String programName;
        private String memo;

        private List<String> urlIdxs;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyReq {
        @Min(0)
        private Long programIdx;

        @NotBlank
        @Size(min = 1, max = 100)
        private String programName;
        private String memo;

        private List<String> urlIdxs;
    }

    @Data
    @NoArgsConstructor
    public static class UrlSearch {
        @NotBlank
        @Size(min = 1, max = 100)
        private String keyword;

    }

    @Data
    @NoArgsConstructor
    public static class Search {
        @NotBlank
        @Size(min = 1, max = 100)
        private String keyword;

    }
}
