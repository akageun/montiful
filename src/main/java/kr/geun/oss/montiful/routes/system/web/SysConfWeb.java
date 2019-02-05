package kr.geun.oss.montiful.routes.system.web;

import kr.geun.oss.montiful.app.system.dto.SysConfDTO;
import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import kr.geun.oss.montiful.core.pagination.PaginationInfo;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

	@Autowired
	private SysConfService sysConfService;

	@GetMapping("/configuration")
	public ModelAndView systemConfigPage(@Valid SysConfDTO.PageReq param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		Page<SysConfEntity> rtnList = sysConfService.page(PageRequestWrapper.of(param.getPageNumber(), 20, Sort.by(Sort.Direction.DESC, "confCd")));

		ModelAndView mav = new ModelAndView();

		mav.addObject("resultList", rtnList);
		mav.addObject("paramInfo", param);
		mav.addObject("pagination",
			PaginationInfo.of(rtnList.getNumber(), rtnList.getNumberOfElements(), rtnList.getTotalElements(), rtnList.getTotalPages(), 3));

		mav.setViewName("system/configuration");
		return mav;
	}
}
