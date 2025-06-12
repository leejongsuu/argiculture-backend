package com.aivle.agriculture.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"isSuccess", "message", "result"})
public record Response<T>(Boolean isSuccess, String message, T result) {

    public static <T> Response<T> of(SuccessCode code, T result) {
        return new Response<>(true, code.getMessage(), result);
    }

    public static <T> Response<T> of(ErrorCode code, T result) {
        return new Response<>(false, code.getMessage(), result);
    }

    // 커스텀 메시지용
    public static <T> Response<T> of(boolean success, String message, T result) {
        return new Response<>(success, message, result);
    }
}
