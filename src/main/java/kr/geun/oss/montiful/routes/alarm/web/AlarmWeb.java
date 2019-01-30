package kr.geun.oss.montiful.routes.alarm.web;

import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.dto.AlarmDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 *
 *
 * @author 김형근
 */
@Slf4j
@Controller
public class AlarmWeb {

	@Autowired
	private AlarmService alarmService;

	@GetMapping("/alarm")
	public ModelAndView getAlarmPage(@Valid AlarmDTO.Page param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "/err/notFound");
		}

		Page<AlarmEntity> rtnList = alarmService.page(PageRequestWrapper.of(param.getPageNumber(), 20, Sort.by(Sort.Direction.DESC, "alarmIdx")));

		ModelAndView mav = new ModelAndView();

		mav.addObject("alarmChannelCd", AlarmChannelCd.values());
		mav.addObject("resultList", rtnList);
		mav.addObject("paramInfo", param);
		mav.addObject("pagination",
			PaginationInfo.of(rtnList.getNumber(), rtnList.getNumberOfElements(), rtnList.getTotalElements(), rtnList.getTotalPages(), 3));

		mav.setViewName("alarm/alarmManage");

		return mav;
	}
}
