package kr.geun.oss.montiful.routes.manage.program.web;

import kr.geun.oss.montiful.app.program.dto.ProgramDTO;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.service.ProgramService;
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
import java.util.Optional;

/**
 * Program Web Controller
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/manage")
public class ManageProgramWeb {

	@Autowired
	private ProgramService programService;

	/**
	 * Program Page
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@GetMapping(value = "/program")
	public ModelAndView getProgramPage(@Valid ProgramDTO.PageReq param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		Page<ProgramEntity> rtnList = programService.page(
			PageRequestWrapper.of(param.getPageNumber(), 20, Sort.by(Sort.Direction.DESC, "programIdx")));

		ModelAndView mav = new ModelAndView();

		mav.addObject("paramInfo", param);
		mav.addObject("resultList", rtnList);
		mav.addObject("pagination",
			PaginationInfo.of(rtnList.getNumber(), rtnList.getNumberOfElements(), rtnList.getTotalElements(), rtnList.getTotalPages(), 3));

		mav.setViewName("manage/program/programManage");
		return mav;
	}
}
