package kr.geun.oss.montiful.app.user.service;

import kr.geun.oss.montiful.app.user.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * User Service Interface
 *
 * @author akageun
 */
public interface UserService {

	/**
	 * List Page Service
	 *
	 * @param pageable
	 * @return
	 */
	Page<UserEntity> page(Pageable pageable);

	/**
	 * Get
	 *
	 * @param userId
	 * @return
	 */
	Optional<UserEntity> get(String userId);

	/**
	 * Add
	 *
	 * @param param
	 */
	void add(UserEntity param);

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
	void login(String userId, String passWd, boolean remember, HttpServletRequest req, HttpServletResponse res) throws Exception;

	/**
	 * Logout
	 *
	 * @param req
	 * @param res
	 */
	void logout(HttpServletRequest req, HttpServletResponse res);
}
