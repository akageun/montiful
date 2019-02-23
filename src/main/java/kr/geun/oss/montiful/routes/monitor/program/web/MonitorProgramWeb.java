package kr.geun.oss.montiful.routes.monitor.program.web;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import kr.geun.oss.montiful.core.pagination.PaginationInfo;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import kr.geun.oss.montiful.core.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 *
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/monitor")
public class MonitorProgramWeb extends BaseController {

	@Autowired
	private ProgramService programService;

	@GetMapping("/program")
	public ModelAndView getMonitorProgramList(@Valid MonitorDTO.PageReq param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		PageRequest pageRequest = PageRequestWrapper.of(param.getPageNumber(), 20, Sort.by(Sort.Direction.DESC, "programIdx"));

		Page<ProgramEntity> rtnList = programService.page(pageRequest, "", "");

		ModelAndView mav = new ModelAndView("monitor/program/programList");

		mav.addObject("paramInfo", param);
		mav.addObject("resultList", rtnList);
		mav.addObject("pagination",
			PaginationInfo.of(rtnList.getNumber(), rtnList.getNumberOfElements(), rtnList.getTotalElements(), rtnList.getTotalPages(), 3));

		return mav;
	}

	@GetMapping("/program/{programIdx}")
	public ModelAndView getMonitorProgram(@Valid MonitorDTO.ViewerReq param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		Optional<ProgramEntity> optionalProgramEntity = programService.get(param.getProgramIdx());
		if (optionalProgramEntity.isPresent() == false) {
			return CmnUtils.mav(HttpStatus.NOT_FOUND, "err/notFound");
		}

		ModelAndView mav = new ModelAndView("monitor/program/programSingle");
		mav.addObject("result", optionalProgramEntity.get());
		mav.addObject("paramInfo", param);

		return mav;
	}
}
