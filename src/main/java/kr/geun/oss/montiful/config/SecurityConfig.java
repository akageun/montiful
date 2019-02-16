package kr.geun.oss.montiful.config;

import kr.geun.oss.montiful.app.user.cd.AuthorityCd;
import kr.geun.oss.montiful.app.user.security.service.SimpleDetailSecurityService;
import kr.geun.oss.montiful.app.user.security.jwt.JwtProvider;
import kr.geun.oss.montiful.app.user.security.jwt.filter.JwtAuthenticationFilter;
import kr.geun.oss.montiful.app.user.security.jwt.impl.JwtProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 *
 * @author akageun
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
	public void configure(WebSecurity web) throws Exception {
		//@formatter:off
		web.ignoring()
			.antMatchers("/resources/**")
			.antMatchers("/webjars/**")
			.antMatchers("/static/**")
			.antMatchers("/favicon.ico"); // #3
		//@formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.addFilterBefore(new JwtAuthenticationFilter(jwtProvider()),
					UsernamePasswordAuthenticationFilter.class)
			.cors()
			.and()
				.formLogin().loginPage("/login")

			.and()
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/user/api/v1/logout")
					.authenticated()
				.antMatchers("/login")
					.permitAll()
				.antMatchers("/user/**")
					.permitAll()
				.antMatchers("/","/dashboard")
					.hasAnyRole(AuthorityCd.NORMAL.name(), AuthorityCd.MANAGER.name(), AuthorityCd.SUPER_ADMIN.name())
				.antMatchers("/monitor*")
					.hasAnyRole(AuthorityCd.NORMAL.name())
				.antMatchers("/manage*")
					.hasAnyRole(AuthorityCd.MANAGER.name())
				.antMatchers("/system*")
					.hasAnyRole(AuthorityCd.SUPER_ADMIN.name())
			.anyRequest()
				.authenticated()
			.and()
				.headers().frameOptions().disable()
			.and()
				.exceptionHandling()
					.accessDeniedPage("/403");
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
