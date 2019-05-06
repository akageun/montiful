package kr.geun.oss.montiful.app.program.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author akageun
 */
public class ProgramDTO {

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
}
