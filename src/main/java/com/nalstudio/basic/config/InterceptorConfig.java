package com.nalstudio.basic.config;

import com.nalstudio.basic.common.interceptor.AccessLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfig implements WebMvcConfigurer{
    //원하는 URI에 적절한 패턴을 적용하여 인터셉터를 지정한다.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLoggingInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    //AccessLoggingInterceptor를 스프링 빈으로 정의한다.
    @Bean
    public HandlerInterceptor accessLoggingInterceptor() {
        return new AccessLoggingInterceptor();
    }
}
