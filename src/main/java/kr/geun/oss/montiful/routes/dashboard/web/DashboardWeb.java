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

    /**
     * Dashboard
     *
     * @return
     */
    @GetMapping(value = {"/"})
    public String dashboard() {
        return "dashboard";
    }

}
