package kr.geun.oss.montiful.core.utils;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Common Utils
 *
 * @author akageun
 */
public class CmnUtils {

	public static ModelAndView mav(String viewName) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.OK);
		mav.setViewName(viewName);
		return mav;
	}

	public static ModelAndView mav(HttpStatus status, String viewName) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(status);
		mav.setViewName(viewName);
		return mav;
	}

	public static <T> T copyProperties(Object param, Class<T> tClass) throws IllegalAccessException, InstantiationException {
		T newInstance = tClass.newInstance();
		BeanUtils.copyProperties(param, newInstance);

		return newInstance;
	}

	public static <E extends Enum<E>> E defaultEnumCode(E enm, E defaultEnum) {

		if (enm == null) {
			return defaultEnum;
		}

		return enm;
	}

	public static <E extends Enum<E>> E defaultEnumCodeStr(Class<E> enm, String enumValue, E defaultEnum) {

		if (enumValue == null) {
			return defaultEnum;
		}

		E tmpEnum = EnumUtils.getEnum(enm, enumValue);
		if (tmpEnum == null) {
			return defaultEnum;
		}

		return tmpEnum;
	}
}
