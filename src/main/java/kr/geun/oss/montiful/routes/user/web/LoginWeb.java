package kr.geun.oss.montiful.routes.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 로그인 관련 Controller
 *
 * @author akageun
 */
@Slf4j
@Controller
public class LoginWeb {

    /**
     * 로그인 페이지
     *
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("user/login");
    }
}
