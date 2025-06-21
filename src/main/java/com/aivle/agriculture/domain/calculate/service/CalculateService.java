package com.aivle.agriculture.domain.calculate.service;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;
import com.aivle.agriculture.domain.calculate.dto.CalculateResponse;

public interface CalculateService {
    CalculateResponse calculate(CalculateRequest request);
}