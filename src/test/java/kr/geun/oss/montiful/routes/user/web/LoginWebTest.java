package kr.geun.oss.montiful.routes.user.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginWeb.class)
public class LoginWebTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void loginPageTest() throws Exception {
        //@formatter:off
		mvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/login"))
			;
		//@formatter:on
    }
}
