package com.nalstudio.basic.common.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class AccessLoggingInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestUri = request.getRequestURI();
        log.info("requestURL: " + requestUri);
        return true;
    }

    //모든 input logging 처리
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String requestUri = request.getRequestURI();

        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if(remoteAddr == null) {
            remoteAddr = request.getRemoteAddr();
        }

        log.info("requestURL: " + requestUri);

        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            Class<?> clazz = method.getDeclaringClass();

            String className = clazz.getName();
            String classSimpleName = clazz.getSimpleName();
            String methodName = method.getName();

            log.info("[ACCESS CONTROLLER]" + className + " . " + methodName);

        } else {
            log.info("handle: " + handler);
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
