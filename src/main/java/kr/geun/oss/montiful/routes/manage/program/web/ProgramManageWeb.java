package kr.geun.oss.montiful.routes.manage.program.web;

import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import kr.geun.oss.montiful.app.program.cd.ProgramManageSortTypeCd;
import kr.geun.oss.montiful.app.program.dto.ProgramDTO;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.core.constants.Const;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import kr.geun.oss.montiful.core.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Program Web Controller
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/manage")
public class ProgramManageWeb extends BaseController {

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
	public ModelAndView getProgramPage(
			@Valid ProgramDTO.PageReq param,
			BindingResult result
	) {

		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		ProgramManageSortTypeCd sortTypeCd = CmnUtils.defaultEnumCodeStr(ProgramManageSortTypeCd.class, param.getSot(), ProgramManageSortTypeCd.IDX);

		Pageable pageable = setCmnPageable(param, sortTypeCd);
		Page<ProgramDTO.PageRes> rtnList = programService.page(pageable, param.getSt(), param.getSv());

		ModelAndView mav = new ModelAndView("manage/program/programManage");

		mav.addObject("searchTypeCd", ProgramManageSearchTypeCd.values());

		mav.addObject("sortTypeCd", ProgramManageSortTypeCd.values());
		mav.addObject("sortDirectionCd", Sort.Direction.values());

		mav.addObject("paramInfo", param);
		mav.addObject("resultList", rtnList);

		setPage(mav, rtnList, Const.Page.DEFAULT_PAGE_BLOCK_SIZE);

		return mav;
	}

}
