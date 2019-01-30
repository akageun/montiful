package kr.geun.oss.montiful.core.validation.validator;

import kr.geun.oss.montiful.core.validation.annotation.EnumValid;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 *
 * @author akageun
 */
public class CustomEnumValidator implements ConstraintValidator<EnumValid, String> {

	private Set<String> targetEnumList = new TreeSet<>();

	@Override
	public void initialize(EnumValid constraintAnnotation) {
		Class<? extends Enum<?>> enumClass = constraintAnnotation.targetEnum();

		Enum[] enumValArr = enumClass.getEnumConstants();
		for (Enum anEnum : enumValArr) {
			targetEnumList.add(StringUtils.upperCase(anEnum.toString()));
		}

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (StringUtils.isBlank(value)) {
			return false;
		}

		return targetEnumList.contains(StringUtils.upperCase(value));
	}
}
