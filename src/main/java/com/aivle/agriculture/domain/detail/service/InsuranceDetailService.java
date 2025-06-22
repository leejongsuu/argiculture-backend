package com.aivle.agriculture.domain.detail.service;

import com.aivle.agriculture.domain.detail.dto.InsuranceDetailResponse;
import com.aivle.agriculture.domain.detail.entity.InsuranceProduct;
import com.aivle.agriculture.domain.detail.enums.InsuredItem;
import com.aivle.agriculture.domain.detail.repository.InsuranceProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceDetailService {
    private final InsuranceProductRepository repository;

    public InsuranceDetailResponse getDetailByCropType(InsuredItem cropType) {
        InsuranceProduct product = repository.findByCropType(cropType)
                .orElseThrow(() -> new IllegalArgumentException("보험 정보 없음"));

        return new InsuranceDetailResponse(
                product.getCropType(),
                product.getSummary(),
                product.getPdfUrl(),
                new InsuranceDetailResponse.InsurancePeriod(
                        product.getStartDate(),
                        product.getEndDate()
                )
        );
    }
}
