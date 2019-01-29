package kr.geun.oss.montiful.routes.url.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Controller
public class UrlWeb {

	@GetMapping("/url")
	public ModelAndView getUrlPage() {
		return new ModelAndView();
	}

	@GetMapping("/url/add")
	public ModelAndView addUrlForm() {
		return new ModelAndView();
	}

	@GetMapping("/url/modify")
	public ModelAndView modifyUrlForm() {
		return new ModelAndView();
	}
}
