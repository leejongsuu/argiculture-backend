package com.aivle.agriculture.domain.calculate.dto.loss;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

// 오디
public record MulberryLossCalcRequest(
        InsuredItem insuredItem,                // 보험 품목
        CropType cropType,                      // 작물 종류
        CoverageType coverageType,              // 보장 종류
        BigDecimal insuredAmount,               // 보험가입금액
        BigDecimal deductibleRate,              // 자기부담비율
        BigDecimal averageFruitCount,              // 평년결실수
        BigDecimal surveyedFruitCount,             // 조사결실수
        BigDecimal uncompensatedReductionFruitCount// 미보상감수결실수
) {
}
