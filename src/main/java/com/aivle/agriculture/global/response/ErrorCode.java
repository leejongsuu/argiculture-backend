package com.aivle.agriculture.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    
    // TODO: 보험금 계산 개발 끝나면 지우기
    // FastAPI 통신 관련 에러
    FASTAPI_CONNECTION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "보험금 계산 서비스에 연결할 수 없습니다."),
    FASTAPI_CALCULATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "보험금 계산 중 오류가 발생했습니다."),

    // 5xx 서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
