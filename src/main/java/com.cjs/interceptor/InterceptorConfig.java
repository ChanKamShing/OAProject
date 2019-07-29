package com.cjs.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public PopedomInterceptor getPopedomInterceptor() {
        return new PopedomInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration login = registry.addInterceptor((HandlerInterceptor) getLoginInterceptor());

        login.addPathPatterns(new String[]{"/*/*" , "/*/*/*"});
        login.excludePathPatterns("/oa/login" , "/css/**", "/docs/**", "/fonts/**","/images/**","/resources/**");

        InterceptorRegistration popedom = registry.addInterceptor((HandlerInterceptor) getPopedomInterceptor());
        popedom.addPathPatterns("/*/*/*");
        popedom.excludePathPatterns("/oa/login", "/css/**", "/docs/**", "/fonts/**", "/images/**", "/resources/**");
    }
}
