package kr.geun.oss.montiful.routes.dashboard.web;

import kr.geun.oss.montiful.app.monitor.service.MonitorHistService;
import kr.geun.oss.montiful.app.url.repo.UrlMonitorHistRepo;
import kr.geun.oss.montiful.app.url.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dashboard Web Controller
 *
 * @author akageun
 */
@Controller
public class DashboardWeb {

	@GetMapping(value = {"/403"})
	public String welcome403() {
		return "/dashboard";
	}

	/**
	 * Dashboard
	 *
	 * @return
	 */
	@GetMapping(value = {"/"})
	public String welcome() {
		return "redirect:/dashboard";
	}

	@Autowired
	private UrlService urlService;

	@Autowired
	private MonitorHistService monitorHistService;

	@Autowired
	private UrlMonitorHistRepo urlMonitorHistRepo;

	/**
	 * Dashboard
	 *
	 * @return
	 */
	@GetMapping(value = {"/dashboard"})
	public String dashboard(Model model) {

		model.addAttribute("histList", monitorHistService.getUrlHistList());
		model.addAttribute("statusCntList", urlService.getStatusCntForDashboard());
		model.addAttribute("urlMonitorHistList", urlMonitorHistRepo.findUrlMonitorHistEntities(10L));

		return "dashboard";
	}

}
