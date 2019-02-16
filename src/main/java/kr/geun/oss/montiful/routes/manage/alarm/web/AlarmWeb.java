package kr.geun.oss.montiful.routes.manage.alarm.web;

import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.dto.AlarmDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.core.pagination.PageRequestWrapper;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import kr.geun.oss.montiful.core.web.BaseController;
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
 * Alarm Manage Web Controller
 *
 * @author akageun
 */
@Slf4j
@Controller
@RequestMapping("/manage")
public class AlarmWeb extends BaseController {

	@Autowired
	private AlarmService alarmService;

	/**
	 * Alarm Page
	 *
	 * @param param
	 * @param result
	 * @return
	 */
	@GetMapping("/alarm")
	public ModelAndView getAlarmPage(@Valid AlarmDTO.Page param, BindingResult result) {
		if (result.hasErrors()) {
			return CmnUtils.mav(HttpStatus.BAD_REQUEST, "err/notFound");
		}

		Page<AlarmEntity> rtnList = alarmService.page(PageRequestWrapper.of(param.getPageNumber(), 20, Sort.by(Sort.Direction.DESC, "alarmIdx")));

		ModelAndView mav = new ModelAndView();

		mav.addObject("alarmChannelCd", AlarmChannelCd.values());
		mav.addObject("resultList", rtnList);
		setPage(mav, rtnList, 3);

		mav.setViewName("manage/alarm/alarmManage");

		return mav;
	}
}
