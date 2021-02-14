package kr.geun.oss.montiful.routes.dashboard.web;

import kr.geun.oss.montiful.app.monitor.service.MonitorHistService;
import kr.geun.oss.montiful.app.url.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(value = DashboardWeb.class)
public class DashboardWebTest {

    @MockBean
    private UrlService urlService;

    @MockBean
    private MonitorHistService monitorHistService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mvc;

    @WithMockUser(username = "spring", roles = {"NORMAL"})
    @Test
    public void rootTest() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"))
        ;
    }

    @WithMockUser(username = "spring", roles = {"NORMAL"})
    @Test
    public void dashboardTest() throws Exception {

        mvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("histList", "statusCntList", "urlMonitorHistList"))
        ;
    }

}
