package com.aivle.agriculture.domain.calculate.dto.loss;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

// 무화과2 (사고발생 월 8월 이후)
public record FigLossAfterAccidentCalcRequest(
        InsuredItem insuredItem,                // 보험 품목
        CropType cropType,                      // 작물 종류
        CoverageType coverageType,              // 보장 종류
        BigDecimal insuredAmount,               // 보험가입금액
        BigDecimal deductibleRate,              // 자기부담비율
        int accidentOccurrenceMonth,            // 사고발생 월 (1~12)
        BigDecimal preHarvestAccidentDamageRate,// 수확전사고 피해율
        BigDecimal elapsedRate,                 // 경과비율
        BigDecimal postHarvestDamageRate        // 결과지 피해율
) {
}
