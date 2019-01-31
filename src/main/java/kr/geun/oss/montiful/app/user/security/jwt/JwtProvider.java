package kr.geun.oss.montiful.app.user.security.jwt;

import io.jsonwebtoken.ClaimJwtException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author akageun
 */
public interface JwtProvider {

	/**
	 * 인증 정보 가져오기
	 *
	 * @param req
	 * @return
	 */
	Authentication getAuthentication(HttpServletRequest req) throws ClaimJwtException;

	void logout(HttpServletRequest req, HttpServletResponse res);

	void generateUserCookie(Authentication authentication, Boolean rememberMe, HttpServletResponse res);

}
