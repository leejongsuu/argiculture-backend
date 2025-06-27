package com.aivle.agriculture.global.security.jwt;

import com.aivle.agriculture.global.exception.CustomException;
import com.aivle.agriculture.global.response.ErrorCode;
import com.aivle.agriculture.global.response.Response;
import com.aivle.agriculture.global.response.ResponseFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final ResponseFactory responseFactory;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.warn("인증 예외 발생: {}", authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Response<Void> errorBody = responseFactory
                .error(new CustomException(ErrorCode.UNAUTHORIZED, "인증이 필요합니다."))
                .getBody();

        objectMapper.writeValue(response.getWriter(), errorBody);
    }
}