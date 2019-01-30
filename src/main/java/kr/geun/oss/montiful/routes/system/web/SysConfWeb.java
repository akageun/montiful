package kr.geun.oss.montiful.routes.system.web;

import kr.geun.oss.montiful.app.system.dto.SysConfDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * System Configuration Web
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class SysConfWeb {

	@GetMapping("/configuration")
	public ModelAndView systemConfigPage(@Valid SysConfDTO.PageReq param, BindingResult result) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("system/configuration");
		return mav;
	}
}
