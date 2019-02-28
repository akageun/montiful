package kr.geun.oss.montiful.config;

import kr.geun.oss.montiful.core.pagination.dialect.PaginationDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Web Mvc Configuration
 *
 * @author akageun
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 정적 자원에 대한 설정
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//@formatter:off
		VersionResourceResolver versionResourceResolver = new VersionResourceResolver();
		versionResourceResolver.addFixedVersionStrategy("0.0.1", "/**");

		registry
			.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/static/")
					.setCachePeriod(60 * 60)
					.resourceChain(true)
					.addResolver(versionResourceResolver);

		registry
			.addResourceHandler("/webjars/**")
				.addResourceLocations("/webjars/")
					.resourceChain(false);

				registry
			.addResourceHandler("/favicon.ico")
				.addResourceLocations("classpath:/static/")
					.setCachePeriod(60 * 60)
					.resourceChain(true)
					.addResolver(new PathResourceResolver());

		registry
			.addResourceHandler("/robots.txt")
				.addResourceLocations("classpath:/static/");
		//@formatter:on
	}

	@Bean
	public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
		return new ResourceUrlEncodingFilter();
	}

	/**
	 * @Value 어노테이션을 사용하기 위한 설정
	 *
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public SpringResourceTemplateResolver springResourceTemplateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(
		//@formatter:off
		LayoutDialect layoutDialect,
		SpringResourceTemplateResolver springResourceTemplateResolver
		//@formatter:on
	) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(springResourceTemplateResolver);
		templateEngine.addDialect(new PaginationDialect());
		templateEngine.addDialect(new Java8TimeDialect());
		templateEngine.addDialect(layoutDialect);
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
