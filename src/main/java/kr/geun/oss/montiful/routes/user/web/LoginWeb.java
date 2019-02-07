package kr.geun.oss.montiful.routes.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Controller
public class LoginWeb {

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
}
