package kr.geun.oss.montiful.app.user.security.service;

import kr.geun.oss.montiful.app.user.repo.UserAuthorityRepo;
import kr.geun.oss.montiful.app.user.repo.UserRepo;
import kr.geun.oss.montiful.core.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SimpleDetailSecurityService.class})
public class SimpleDetailSecurityServiceTest {

	@Autowired
	private SimpleDetailSecurityService simpleDetailSecurityService;

	@MockBean
	private UserRepo userRepo;

	@MockBean
	private UserAuthorityRepo userAuthorityRepo;

	@Test(expected = UsernameNotFoundException.class)
	public void notfoundUser() {
		given(userRepo.findById(Const.System.systemAdminUserId)).willReturn(Optional.empty());

		simpleDetailSecurityService.loadUserByUsername(Const.System.systemAdminUserId);
	}

}