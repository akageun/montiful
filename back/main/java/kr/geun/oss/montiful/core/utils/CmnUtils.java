package kr.geun.oss.montiful.core.utils;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
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

    public static <E extends Enum<E>> boolean isSearchable(E searchType, String searchValue) {
        return searchType != null && StringUtils.isNotBlank(searchValue);
    }

    /**
     * 문자열을 지정한 Enum class 에서 enum 값으로 가지고옴
     * - 없을 경우 default 값으로 리턴
     *
     * @param enumClass
     * @param enumValue
     * @param defaultEnum
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> E defaultEnumCode(Class<E> enumClass, String enumValue, E defaultEnum) {

        if (enumValue == null) {
            return defaultEnum;
        }

        E tmpEnum = EnumUtils.getEnum(enumClass, enumValue);
        if (tmpEnum == null) {
            return defaultEnum;
        }

        return tmpEnum;
    }
}
