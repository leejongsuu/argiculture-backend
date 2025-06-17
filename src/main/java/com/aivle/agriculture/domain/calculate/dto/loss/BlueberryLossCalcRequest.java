package com.aivle.agriculture.domain.calculate.dto.loss;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

// 블루베리
public record BlueberryLossCalcRequest(
        InsuredItem insuredItem,                // 보험 품목
        CropType cropType,                      // 작물 종류
        CoverageType coverageType,              // 보장 종류
        BigDecimal insuredAmount,               // 보험가입금액
        BigDecimal deductibleRate,              // 자기부담비율
        BigDecimal fruitDamageRate,             // 과실손해피해율
        BigDecimal uncompensatedRate,           // 미보상비율
        @JsonProperty(required = false)
        BigDecimal finalFlowerDamageRate        // 최종 꽃 피해율 (꽃 피해조사 시, 아니면 null)
) {
}
