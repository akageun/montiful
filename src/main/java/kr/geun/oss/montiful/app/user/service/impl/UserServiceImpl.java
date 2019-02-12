package kr.geun.oss.montiful.app.user.service.impl;

import kr.geun.oss.montiful.app.user.cd.AuthorityCd;
import kr.geun.oss.montiful.app.user.models.UserAuthorityEntity;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.repo.UserAuthorityRepo;
import kr.geun.oss.montiful.app.user.repo.UserRepo;
import kr.geun.oss.montiful.app.user.security.service.SimpleDetailSecurityService;
import kr.geun.oss.montiful.app.user.security.jwt.JwtProvider;
import kr.geun.oss.montiful.app.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserAuthorityRepo userAuthorityRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
	@Override
	public Page<UserEntity> page(Pageable pageable) {
		return userRepo.findAll(pageable);
	}

	/**
	 * Get
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Optional<UserEntity> get(String userId) {
		return userRepo.findById(userId);
	}

	/**
	 * Add
	 *
	 * @param param
	 */
	@Override
	public void add(UserEntity param) {
		param.setPassWd(passwordEncoder.encode(param.getPassWd()));

		userRepo.save(param);
		//@formatter:off
		userAuthorityRepo.save(
			UserAuthorityEntity.builder()
				.userId(param.getUserId())
				.authorityCd(AuthorityCd.NORMAL.roleCd())
				.createdUserId(param.getUserId())
				.updatedUserId(param.getUserId())
				.build()
		);
		//@formatter:on
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
	@Override
	public void login(String userId, String passWd, boolean remember, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
	@Override
	public void logout(HttpServletRequest req, HttpServletResponse res) {
		jwtProvider.logout(req, res);
		SecurityContextHolder.clearContext();
	}
}
