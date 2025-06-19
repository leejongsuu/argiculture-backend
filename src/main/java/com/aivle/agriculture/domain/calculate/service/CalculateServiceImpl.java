package com.aivle.agriculture.domain.calculate.service;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;
import com.aivle.agriculture.domain.calculate.dto.CalculateResponse;
import com.aivle.agriculture.global.exception.CustomException;
import com.aivle.agriculture.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    private final WebClient webClient;

    @Value("${fastapi.base-url:http://localhost:8000}")
    private String fastApiBaseUrl;

    @Override
    public Mono<CalculateResponse> calculate(CalculateRequest request) {
        log.info("보험금 계산 요청: {}", request);
        
        return webClient.post()
                .uri(fastApiBaseUrl + "/api/calc")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CalculateResponse.class)
                .onErrorMap(WebClientResponseException.class, e -> 
                    new CustomException(ErrorCode.FASTAPI_CALCULATION_ERROR, 
                        "보험금 계산 서비스에서 오류가 발생했습니다: " + e.getStatusCode()))
                .onErrorMap(Exception.class, e -> 
                    new CustomException(ErrorCode.FASTAPI_CONNECTION_ERROR, 
                        "보험금 계산 서비스에 연결할 수 없습니다: " + e.getMessage()));
    }
} 