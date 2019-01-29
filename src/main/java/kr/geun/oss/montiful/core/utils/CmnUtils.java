package kr.geun.oss.montiful.core.utils;

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
}
