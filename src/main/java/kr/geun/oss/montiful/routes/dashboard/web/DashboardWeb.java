package kr.geun.oss.montiful.routes.dashboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 *
 * @author akageun
 */
@Controller
public class DashboardWeb {

    @GetMapping(value = {"/", "/dashboard"})
    public String dashboard() {
        return "dashboard";
    }

}
