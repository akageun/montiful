package kr.geun.oss.montiful.routes.dashboard.web;

import kr.geun.oss.montiful.app.monitor.service.MonitorHistService;
import kr.geun.oss.montiful.app.url.service.UrlService;
import kr.geun.oss.montiful.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

    /**
     * Dashboard
     * - Redirect
     *
     * @return
     */
    @GetMapping(value = {"/"})
    public RedirectView welcome() {
        return new RedirectView("/dashboard");
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
        mav.addObject("urlMonitorHistList", urlService.getUrlMonitorHistList(10L));

        return mav;
    }

}
