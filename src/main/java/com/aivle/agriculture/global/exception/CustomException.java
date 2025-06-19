package com.aivle.agriculture.global.exception;

import com.aivle.agriculture.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause); // 실제 예외
        this.errorCode = errorCode;
    }
}
