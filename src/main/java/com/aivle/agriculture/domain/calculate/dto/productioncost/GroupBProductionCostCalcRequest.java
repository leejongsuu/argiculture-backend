package com.aivle.agriculture.domain.calculate.dto.productioncost;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

/**
 * Group B: 당근, 배추, 무, 파, 시금치, 양상추
 */
public record GroupBProductionCostCalcRequest(
        InsuredItem insuredItem,               // 보험 품목
        CropType cropType,                     // 작물 종류
        CoverageType coverageType,             // 보장 종류
        BigDecimal insuredAmount,              // 보험가입금액
        BigDecimal deductibleRate,             // 자기부담비율
        BigDecimal areaDamageRate,             // 면적피해율
        BigDecimal averageDamageSeverityRate,  // 평균손해정도비율
        BigDecimal uncompensatedRate           // 미보상비율
) {
}
