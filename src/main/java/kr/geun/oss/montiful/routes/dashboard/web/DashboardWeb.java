package kr.geun.oss.montiful.routes.dashboard.web;

import kr.geun.oss.montiful.app.monitor.service.MonitorHistService;
import kr.geun.oss.montiful.app.url.repo.UrlMonitorHistRepo;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Dashboard Web Controller
 *
 * @author akageun
 */
@Controller
public class DashboardWeb extends BaseController {

	@Autowired
	private UrlService urlService;

	@Autowired
	private MonitorHistService monitorHistService;

	@Autowired
	private UrlMonitorHistRepo urlMonitorHistRepo;

	/**
	 * Dashboard
	 * - Redirect
	 *
	 * @return
	 */
	@GetMapping(value = {"/"})
	public String welcome() {
		return "redirect:/dashboard";
	}

	/**
	 * Dashboard
	 *
	 * @return
	 */
	@GetMapping(value = {"/dashboard"})
	public ModelAndView dashboard() {

		ModelAndView mav = new ModelAndView("dashboard");

		mav.addObject("histList", monitorHistService.getUrlHistList());
		mav.addObject("statusCntList", urlService.getStatusCntForDashboard());
		mav.addObject("urlMonitorHistList", urlMonitorHistRepo.findUrlMonitorHistEntities(10L));

		return mav;
	}

}
