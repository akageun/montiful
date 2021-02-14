package kr.geun.oss.montiful.app.template.type;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TemplateType
 *
 * @author akageun
 * @since 2021-02-14
 */
@Getter
public enum TemplateType {
    SIGNUP_CONFIRM_EMAIL(
        Arrays.asList(
            TemplateTargetType.CONFIRM_URL,
            TemplateTargetType.SIGNUP_CONFIRM_CODE
        ),
        Collections.emptyList()
    ),
    PASSWORD_CHANGE_EMAIL(
        Arrays.asList(
            TemplateTargetType.CONFIRM_URL
        ),
        Collections.emptyList()
    );

    private final List<TemplateTargetType> requiredTargetTypes;
    private final List<TemplateTargetType> optionalTargetTypes;

    TemplateType(List<TemplateTargetType> requiredTargetTypes, List<TemplateTargetType> optionalTargetTypes) {
        this.requiredTargetTypes = requiredTargetTypes;
        this.optionalTargetTypes = optionalTargetTypes;
    }
}
