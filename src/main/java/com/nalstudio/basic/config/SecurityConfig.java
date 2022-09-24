package com.nalstudio.basic.config;

import com.nalstudio.basic.common.security.CustomAccessDeniedHandler;
import com.nalstudio.basic.common.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
//시큐리티 애너테이션 활성화를 위한 설정
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security Config======");

        //폼 로그인 기능과 베이직 인증 비활성화
        http.formLogin().disable()
                .httpBasic().disable();

        //CORS 사용자 설정
        http.cors();

        //CSRF 방지 지원 기능 비활성화
        http.csrf().disable();

        //윕 경로 보안 설정
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").access("permitAll")
                .antMatchers("/commonApi/**").access("permitAll")
                .antMatchers("/**").access("permitAll")
                .anyRequest().authenticated();

        //접근 거부 처리자 등록
        http.exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setExposedHeaders(Arrays.asList("Authoriztion", "Content-Disposition"));

        source.registerCorsConfiguration("/**", config);
        return source;
    }

    //접근 거부 처리자 생설
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
