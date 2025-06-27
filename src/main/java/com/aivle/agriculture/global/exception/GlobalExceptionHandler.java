package com.aivle.agriculture.global.exception;

import com.aivle.agriculture.global.response.ErrorCode;
import com.aivle.agriculture.global.response.Response;
import com.aivle.agriculture.global.response.ResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ResponseFactory responseFactory;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(Exception e) {
        log.error("서버 예외 발생 : {}", e.toString());
        return responseFactory.error(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response<Void>> handleCustomException(CustomException e) {
        log.error("커스텀 예외 발생 : {}", e.toString());
        return responseFactory.error(e);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Response<Void>> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
        log.error("인증 부족 예외 발생 : {}", e.toString());
        return responseFactory.error(new CustomException(ErrorCode.UNAUTHORIZED, e.getMessage()));
    }
}



