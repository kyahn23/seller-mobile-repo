package com.pentas.sellermobile.common.conf;

import com.pentas.sellermobile.common.interceptor.CustomInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
	CustomInterceptor customInterceptor;
    
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/resources/**") // 예외처리 대상.
        .excludePathPatterns("/api/**") // 예외처리 대상.
        .excludePathPatterns("/static/**");
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/");
		registry.addResourceHandler("/publisher/**")
			.addResourceLocations("/publisher/");
		registry.addResourceHandler("/test/**")
			.addResourceLocations("/test/");
		registry.addResourceHandler("/css/**")
			.addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/images/**")
			.addResourceLocations("classpath:/static/images/");
		registry.addResourceHandler("/js/**")
			.addResourceLocations("classpath:/static/js/");
	}
	
}
