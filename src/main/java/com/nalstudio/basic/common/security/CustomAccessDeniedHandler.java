package com.nalstudio.basic.common.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nalstudio.basic.exception.ApiErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("======CustomAccessDeniedHandler======");
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setMessage("Access denied");

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(apiErrorInfo);

        response.setContentType("applacation/json; charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(jsonString);
    }
}
