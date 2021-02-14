package kr.geun.oss.montiful.app.template.type;

import lombok.Getter;

/**
 * TemplateTargetType
 *
 * @author akageun
 * @since 2021-02-14
 */
@Getter
public enum TemplateTargetType {
    //#{{}}
    CONFIRM_URL("confirm_url"),
    SIGNUP_CONFIRM_CODE("signup_confirm_code"),

    ;

    private final String targetText;

    TemplateTargetType(String targetText) {
        this.targetText = targetText;
    }
}
