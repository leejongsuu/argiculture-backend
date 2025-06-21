package com.aivle.agriculture.global.response;

import com.aivle.agriculture.global.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ResponseFactory {

    public <T> ResponseEntity<Response<T>> success(SuccessCode code, T result) {
        return ResponseEntity.status(code.getHttpStatus())
                .body(Response.of(code, result));
    }

    public ResponseEntity<Response<Void>> success(SuccessCode code) {
        if (code == SuccessCode.NO_CONTENT) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(code.getHttpStatus())
                .body(Response.of(code, null));
    }

    public ResponseEntity<Response<Void>> error(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.of(e.getErrorCode(), null));
    }

    public <T> ResponseEntity<Response<T>> error(CustomException e, T result) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.of(e.getErrorCode(), result));
    }

    public <T> Mono<Response<T>> successMono(SuccessCode code, T result) {
        return Mono.just(Response.of(code, result));
    }

    public <T> Mono<Response<T>> successMono(SuccessCode code, Mono<T> mono) {
        return mono.map(data -> Response.of(code, data));
    }

    public Mono<Response<Void>> errorMono(CustomException e) {
        return Mono.just(Response.of(e.getErrorCode(), null));
    }
}
