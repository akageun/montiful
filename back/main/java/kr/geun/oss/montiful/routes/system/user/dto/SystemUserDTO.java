package kr.geun.oss.montiful.routes.system.user.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author akageun
 */
public class SystemUserDTO {

    @Getter
    @AllArgsConstructor
    public static class PageReq extends CmnPageModule {
        private String st; //SearchType
        private String sv; //SearchValue TODO : Create Search Custom Valid Annotation(Length...)

    }
}
