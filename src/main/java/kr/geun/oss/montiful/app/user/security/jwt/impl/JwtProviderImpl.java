package kr.geun.oss.montiful.app.user.security.jwt.impl;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.geun.oss.montiful.app.user.security.jwt.JwtProvider;
import kr.geun.oss.montiful.core.utils.SecUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;

/**
 *
 *
 * @author akageun
 */
@Slf4j
public class JwtProviderImpl implements JwtProvider, InitializingBean {

	private static final String SECRET_KEY = "motifulSecretKey!!@@";

	private static final String JWT_COOKIE_STRING = "montiful_login";

	private static final String AUTHORITIES_KEY_NM = "auth";

	private static final long TOKEN_EXPIRE = 3600;
	private static final long REMEMBER_TOKEN_EXPIRE = 3600; //TODO : 값 변경해야함.

	private static final long TOKEN_EXPIRE_MS = TOKEN_EXPIRE * 1000; //1Hours
	private static final long REMEMBER_TOKEN_EXPIRE_MS = REMEMBER_TOKEN_EXPIRE * 1000; //1Hours

	private static final String TOKEN_CREATED_AT = "tca";

	private static final String COOKIE_PATH = "/";

	/**
	 * 토큰 생성
	 *
	 * @param authentication
	 * @return
	 */
	private String generatorToken(Authentication authentication, Boolean rememberMe) {
		String authorities = SecUtils.getAuthorities(authentication.getAuthorities());

		Date expireDate;
		if (rememberMe) {
			expireDate = new Date(System.currentTimeMillis() + REMEMBER_TOKEN_EXPIRE_MS);
		} else {
			expireDate = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_MS);
		}

		//@formatter:off
		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY_NM, authorities)
			.claim(TOKEN_CREATED_AT, new Date())
			.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
			.setExpiration(expireDate)
			.compact();
		//@formatter:on
	}

	/**
	 * 인증 정보 가져오기
	 *
	 * @param req
	 * @return
	 */
	@Override
	public Authentication getAuthentication(HttpServletRequest req) throws ClaimJwtException {
		Cookie cookie = WebUtils.getCookie(req, JWT_COOKIE_STRING);

		if (cookie == null) {
			return null;
		}

		String jwt = cookie.getValue();
		if (StringUtils.isBlank(jwt)) {
			return null;
		}

		Claims claims = getJwtClaims(jwt);
		if (claims == null) {
			return null;
		}

		String userId = claims.getSubject();

		if (claims.getExpiration().before(new Date())) {
			return null;
		}

		String authoritiesStr = claims.get(AUTHORITIES_KEY_NM).toString();

		Collection<? extends GrantedAuthority> authorities = SecUtils.mapToGrantedAuthorities(authoritiesStr);

		return new UsernamePasswordAuthenticationToken(userId, null, authorities);
	}

	@Override
	public void logout(HttpServletRequest req, HttpServletResponse res) {
		Cookie cookie = WebUtils.getCookie(req, JWT_COOKIE_STRING);

		if (cookie == null) {
			return;
		}

		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(0);
		res.addCookie(cookie);
	}

	@Override
	public void generateUserCookie(Authentication authentication, Boolean rememberMe, HttpServletResponse res) {
		String token = generatorToken(authentication, rememberMe);

		Cookie cookie = new Cookie(JWT_COOKIE_STRING, token);
		if (rememberMe) {
			cookie.setMaxAge((int) REMEMBER_TOKEN_EXPIRE);
		} else {
			cookie.setMaxAge((int) TOKEN_EXPIRE);
		}

		cookie.setHttpOnly(true); //XSS공격 차단(javascript 등으로 탈취 막음)
		cookie.setPath(COOKIE_PATH);
		res.addCookie(cookie);
	}

	private Claims getJwtClaims(String token) throws ClaimJwtException {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
