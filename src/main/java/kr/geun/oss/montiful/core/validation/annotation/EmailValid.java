package kr.geun.oss.montiful.core.validation.annotation;

import kr.geun.oss.montiful.core.validation.validator.CustomEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom Email Validation
 *
 * @author akageun
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomEmailValidator.class)
public @interface EmailValid {
	String message() default "유효한 이메일 주소를 입력하세요.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
