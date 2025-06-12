package com.aivle.agriculture.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    OK(HttpStatus.OK, "성공입니다."),
    CREATED(HttpStatus.CREATED, "생성이 완료되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "요청이 수락되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 성공적으로 처리되었습니다. 반환할 데이터가 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
