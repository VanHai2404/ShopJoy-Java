package com.edu.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.edu.shop.interceptor.AdminAuthenticationInterceptor;

@Configuration
public class AuthenticationlnterceptorConfig  implements WebMvcConfigurer{
      @Autowired
	 private AdminAuthenticationInterceptor adminAuthenticationInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(adminAuthenticationInterceptor)
	             .addPathPatterns("/admin/**");
		
		
		
	}

}
