package kr.geun.oss.montiful.routes.url.web;

import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Controller
public class UrlWeb {

	@Autowired
	private UrlService urlService;

	@GetMapping("/url")
	public ModelAndView getUrlPage(@Valid UrlDTO.PageReq param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "/err/notFound");
		}

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("paramInfo", param);

		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@GetMapping("/url/add")
	public ModelAndView addUrlForm() {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@GetMapping("/url/modify")
	public ModelAndView modifyUrlForm() {
		ModelAndView mav = new ModelAndView();

		return mav;
	}
}
