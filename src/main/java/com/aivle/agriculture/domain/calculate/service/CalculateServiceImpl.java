package com.aivle.agriculture.domain.calculate.service;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;
import com.aivle.agriculture.domain.calculate.dto.CalculateResponse;
import com.aivle.agriculture.domain.calculate.service.calculator.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    @Override
    public CalculateResponse calculate(CalculateRequest request) {
        Calculator calculator;

        switch (request.getCoverageType()) {
            case LOSS -> calculator = new LossCalculator();
            case PRODUCTION_COST -> calculator = new ProductionCostCalculator();
            case FACILITY_PRODUCTION_COST -> calculator = new FacilityProductionCostCalculator();
            case HARVEST_REDUCTION -> calculator = new HarvestReductionCalculator();
            default -> throw new IllegalStateException("Unexpected value: " + request.getCoverageType());
        }

        return new CalculateResponse(calculator.calculate(request));
    }
} 