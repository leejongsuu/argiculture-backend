package com.aivle.agriculture.domain.calculate.dto.loss;


import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;


// 두릅
public record DureupLossCalcRequest(
        InsuredItem insuredItem,                // 보험 품목
        CropType cropType,                      // 작물 종류
        CoverageType coverageType,              // 보장 종류
        BigDecimal insuredAmount,               // 보험가입금액
        BigDecimal deductibleRate,              // 자기부담비율
        BigDecimal damagedFruitletCount,           // 피해 정아지 수
        BigDecimal totalFruitletCount,             // 총 정아지 수
        BigDecimal uncompensatedRate            // 미보상비율
) {
}
