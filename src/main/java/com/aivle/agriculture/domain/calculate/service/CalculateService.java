package com.aivle.agriculture.domain.calculate.service;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;
import com.aivle.agriculture.domain.calculate.dto.CalculateResponse;
import reactor.core.publisher.Mono;

public interface CalculateService {
    Mono<CalculateResponse> calculate(CalculateRequest request);
}