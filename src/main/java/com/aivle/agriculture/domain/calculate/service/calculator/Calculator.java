package com.aivle.agriculture.domain.calculate.service.calculator;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;

import java.math.BigDecimal;

public interface Calculator {
    BigDecimal calculate(CalculateRequest request);
}
