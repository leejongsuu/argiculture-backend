package com.aivle.agriculture.domain.calculate.dto.loss;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;


// 인삼
public record GinsengLossCalcRequest(
        InsuredItem insuredItem,               // 보험 품목
        CropType cropType,                     // 작물 종류
        CoverageType coverageType,             // 보장 종류
        BigDecimal insuredAmount,           // 보험가입금액
        BigDecimal deductibleRate,          // 자기부담비율
        BigDecimal actualYield,             // 수확량
        BigDecimal standardYieldByCultivationYear,    // 연근별 기준 수확량
        BigDecimal damagedArea,             // 피해면적
        BigDecimal cultivationArea          // 재배면적
) {
}
