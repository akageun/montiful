package kr.geun.oss.montiful.app.user.service;

import kr.geun.oss.montiful.app.user.cd.AuthorityCd;
import kr.geun.oss.montiful.app.user.cd.UserManageSearchTypeCd;
import kr.geun.oss.montiful.app.user.models.UserAuthorityEntity;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.repo.UserAuthorityRepo;
import kr.geun.oss.montiful.app.user.repo.UserRepo;
import kr.geun.oss.montiful.app.user.security.jwt.JwtProvider;
import kr.geun.oss.montiful.app.user.security.service.SimpleDetailSecurityService;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * User Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserAuthorityRepo userAuthorityRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private SimpleDetailSecurityService simpleDetailSecurityService;

    /**
     * List Page Service
     *
     * @param pageable
     * @return
     */
    public Page<UserEntity> page(Pageable pageable, String searchType, String searchValue) {
        UserManageSearchTypeCd searchTypeCd = EnumUtils.getEnum(UserManageSearchTypeCd.class, searchType);

        boolean isSearchMode = false;
        if (CmnUtils.isSearchable(searchTypeCd, searchValue)) {
            isSearchMode = true;

        }

        return userRepo.findPage(pageable, searchTypeCd, searchValue, isSearchMode);
    }

    /**
     * Get
     *
     * @param userId
     * @return
     */
    public Optional<UserEntity> get(String userId) {
        return userRepo.findById(userId);
    }

    /**
     * Add
     *
     * @param param
     */
    @Transactional
    public void add(UserEntity param) {
        userRepo.save(param);

        //TODO: 이동??
        UserAuthorityEntity authParam = UserAuthorityEntity.builder()
                .userId(param.getUserId())
                .authorityCd(AuthorityCd.NORMAL.roleCd())
                .createdUserId(param.getUserId())
                .updatedUserId(param.getUserId())
                .build();

        userAuthorityRepo.save(authParam);
    }

    /**
     * Login
     *
     * @param userId
     * @param passWd
     * @param remember
     * @param req
     * @param res
     * @throws Exception
     */
    public void login(String userId, String passWd, Boolean remember, HttpServletRequest req, HttpServletResponse res) throws Exception {
        UserDetails userDetails = simpleDetailSecurityService.loadUserByUsername(userId);

        UsernamePasswordAuthenticationToken userNmPassWdAuthToken = new UsernamePasswordAuthenticationToken(userDetails, passWd,
                userDetails.getAuthorities());

        Authentication authentication = authenticationManager.authenticate(userNmPassWdAuthToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        jwtProvider.generateUserCookie(authentication, remember, res);
    }

    /**
     * Logout
     *
     * @param req
     * @param res
     */
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        jwtProvider.logout(req, res);
        SecurityContextHolder.clearContext();
    }
}
