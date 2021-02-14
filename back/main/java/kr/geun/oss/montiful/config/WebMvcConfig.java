package kr.geun.oss.montiful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

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
     * @return
     * @Value 어노테이션을 사용하기 위한 설정
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
