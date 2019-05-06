package kr.geun.oss.montiful.routes.manage.program.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 프로그램 관리 DTO
 *
 * @author akageun
 */
public class ManageProgramDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PageReq extends CmnPageModule {
        private String st; //SearchType
        private String sv; //SearchValue TODO : Create Search Custom Valid Annotation(Length...)
    }

    @Getter
    @AllArgsConstructor
    public static class AddReq {

        @NotBlank
        @Size(min = 1, max = 100)
        private String programName;
        private String memo;

        private List<String> urlIdxs;
    }

    @Getter
    @AllArgsConstructor
    public static class ModifyReq {

        @Min(0)
        @NotNull
        private Long programIdx;

        @NotBlank
        @Size(min = 1, max = 100)
        private String programName;
        private String memo;

        private List<String> urlIdxs;
    }

    @Getter
    @AllArgsConstructor
    public static class Search {
        @NotBlank
        @Size(min = 1, max = 100)
        private String keyword;

    }

    @Getter
    @AllArgsConstructor
    public static class UrlSearch {
        @NotBlank
        @Size(min = 1, max = 100)
        private String keyword;

    }
}
