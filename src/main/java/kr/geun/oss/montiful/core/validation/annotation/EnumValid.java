package kr.geun.oss.montiful.core.validation.annotation;

import kr.geun.oss.montiful.core.validation.validator.CustomEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *
 * @author akageun
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomEnumValidator.class)
public @interface EnumValid {
	String message() default "상태값은 공백이거나, 지정되지 않은 상태값을 추가 할 수 없습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends Enum<?>> targetEnum();

	boolean nullable() default false;
}
