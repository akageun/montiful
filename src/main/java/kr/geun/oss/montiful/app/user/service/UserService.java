package kr.geun.oss.montiful.app.user.service;

import kr.geun.oss.montiful.app.user.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 *
 *
 * @author akageun
 */
public interface UserService {

	Page<UserEntity> page(Pageable pageable);

	Optional<UserEntity> get(String userId);

	void save(UserEntity param);

	void login(String userId, String passWd, boolean remember, HttpServletRequest req, HttpServletResponse res) throws Exception;

	void logout(HttpServletRequest req, HttpServletResponse res);
}
