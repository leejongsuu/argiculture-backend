package com.aivle.agriculture.domain.calculate.dto.loss;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

// 무화과1 (사고발생 월 7월 이전)
public record FigLossBeforeAccidentCalcRequest(
        InsuredItem insuredItem,                // 보험 품목
        CropType cropType,                      // 작물 종류
        CoverageType coverageType,              // 보장 종류
        BigDecimal insuredAmount,               // 보험가입금액
        BigDecimal deductibleRate,              // 자기부담비율
        int accidentOccurrenceMonth,            // 사고발생 월 (1~12)
        BigDecimal averageYield,                // 평년수확량
        BigDecimal actualYield,                 // 수확량
        BigDecimal uncompensatedReductionQuantity // 미보상감수량
) {
}
