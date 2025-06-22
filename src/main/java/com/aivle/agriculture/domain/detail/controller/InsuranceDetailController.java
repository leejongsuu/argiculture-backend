package com.aivle.agriculture.domain.detail.controller;

import com.aivle.agriculture.domain.detail.dto.InsuranceDetailResponse;
import com.aivle.agriculture.domain.detail.enums.InsuredItem;
import com.aivle.agriculture.domain.detail.service.InsuranceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceDetailController {
    private final InsuranceDetailService insuranceDetailService;

    @GetMapping("/{cropType}")
    public ResponseEntity<InsuranceDetailResponse> getInsuranceDetail(
            @PathVariable InsuredItem cropType) {
        InsuranceDetailResponse detail = insuranceDetailService.getDetailByCropType(cropType);
        return ResponseEntity.ok(detail);
    }
}