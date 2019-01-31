package kr.geun.oss.montiful.config;

import kr.geun.oss.montiful.app.user.cd.AuthorityCd;
import kr.geun.oss.montiful.app.user.security.SimpleDetailSecurityService;
import kr.geun.oss.montiful.app.user.security.jwt.JwtProvider;
import kr.geun.oss.montiful.app.user.security.jwt.filter.JwtAuthenticationFilter;
import kr.geun.oss.montiful.app.user.security.jwt.impl.JwtProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author 김형근
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public JwtProvider jwtProvider() {
		return new JwtProviderImpl();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtProvider()),
					UsernamePasswordAuthenticationFilter.class)
			.cors()
			.and()
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/url*")
					.hasAnyRole(AuthorityCd.NORMAL.name())
				.antMatchers("/program*")
					.hasAnyRole(AuthorityCd.SUPER_ADMIN.name())
//				.antMatchers("/api/admin/**")
//					.hasAnyRole(AuthorityCd.SUPER_ADMIN.name())
			.anyRequest()
				.permitAll()
			.and()
				.headers().frameOptions().disable()
			.and()
				.exceptionHandling()
					.authenticationEntryPoint((req, res, e) -> {
						res.sendRedirect("/login");
						res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					}) ;
		//@formatter:on

		//super.configure(http);
	}

	@Bean
	@Override
	protected SimpleDetailSecurityService userDetailsService() {
		return new SimpleDetailSecurityService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
