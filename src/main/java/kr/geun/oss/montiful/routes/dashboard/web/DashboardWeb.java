package kr.geun.oss.montiful.routes.dashboard.web;

import org.springframework.stereotype.Controller;
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

	/**
	 * Dashboard
	 *
	 * @return
	 */
	@GetMapping(value = {"/dashboard"})
	public String dashboard() {
		return "dashboard";
	}

}
