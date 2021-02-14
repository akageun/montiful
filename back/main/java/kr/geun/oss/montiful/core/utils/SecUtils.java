package kr.geun.oss.montiful.core.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author akageun
 */
public class SecUtils {

	/**
	 * search User Id By Spring Security Session
	 *
	 * @return
	 */
	public static String userId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return "";
		}

		return authentication.getName();
	}

	/**
	 * Convert
	 *  - String -> Delete<GrantedAuthority>
	 *
	 * @param authoritiesStr
	 * @return
	 */
	public static List<GrantedAuthority> mapToGrantedAuthorities(String authoritiesStr) {
		return mapToGrantedAuthorities(Arrays.asList(authoritiesStr.split(",")));
	}

	/**
	 * Convert
	 *  - Delete<String> -> Delete<GrantedAuthority>
	 *
	 * @param authorities
	 * @return
	 */
	public static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	/**
	 * 문자열로 가져오기
	 *
	 * @param authorities
	 * @return
	 */
	public static String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
	}

}
