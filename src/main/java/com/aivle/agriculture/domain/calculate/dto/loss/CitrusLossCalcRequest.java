package com.aivle.agriculture.domain.calculate.dto.loss;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

//감귤(온주밀감류)
public record CitrusLossCalcRequest(
        InsuredItem insuredItem,                // 보험 품목
        CropType cropType,                      // 작물 종류
        CoverageType coverageType,              // 보장 종류
        BigDecimal insuredAmount,               // 보험가입금액
        BigDecimal deductibleRate,              // 자기부담비율
        BigDecimal averageYield,                // 평년수확량
        BigDecimal standardFruitCount,             // 기준과실수
        BigDecimal uncompensatedRate,           // 미보상비율
        BigDecimal withinGradeDamageFruitCount,    // 등급 내 피해 과실수
        BigDecimal outOfGradeDamageFruitCount      // 등급 외 피해 과실수
) {
}
