package kr.geun.oss.montiful.routes.dashboard.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(value = DashboardWeb.class)
public class DashboardWebTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void dashboardTest() throws Exception {
        //@formatter:off
		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("dashboard"))
			;
		//@formatter:on
    }

}
