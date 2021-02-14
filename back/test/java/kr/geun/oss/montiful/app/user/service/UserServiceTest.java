package kr.geun.oss.montiful.app.user.service;

import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.repo.UserAuthorityRepo;
import kr.geun.oss.montiful.app.user.repo.UserRepo;
import kr.geun.oss.montiful.app.user.security.jwt.JwtProvider;
import kr.geun.oss.montiful.app.user.security.service.SimpleDetailSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserService.class})
public class UserServiceTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private UserAuthorityRepo userAuthorityRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private SimpleDetailSecurityService simpleDetailSecurityService;

    @Autowired
    private UserService userService;

    @Test
    public void getUserSuccessTest() {

        final String userId = "akageun";

        //@formatter:off
        given(userRepo.findById(userId))
            .willReturn(Optional.of(UserEntity.builder()
                .userId(userId)
                .build()));
        //@formatter:on

        Optional<UserEntity> optUserEntity = userService.get(userId);

        Assert.assertNotNull(optUserEntity);
        Assert.assertTrue(optUserEntity.isPresent());

        UserEntity userEntity = optUserEntity.get();

        Assert.assertEquals(userId, userEntity.getUserId());
    }

}
