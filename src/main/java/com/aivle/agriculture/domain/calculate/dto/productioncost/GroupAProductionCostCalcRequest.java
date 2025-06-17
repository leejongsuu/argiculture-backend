package com.aivle.agriculture.domain.calculate.dto.productioncost;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

/**
 * Group A: 고추, 브로콜리
 */
public record GroupAProductionCostCalcRequest(
        InsuredItem insuredItem,               // 보험 품목
        CropType cropType,                     // 작물 종류
        CoverageType coverageType,             // 보장 종류
        BigDecimal insuredAmount,              // 보험가입금액
        BigDecimal deductibleRate,             // 자기부담비율
        BigDecimal residualInsuredAmount,      // 잔존보험가입금액
        BigDecimal elapsedRate,                // 경과비율
        BigDecimal areaDamageRate,             // 면적피해율
        BigDecimal damageSeverityRate,         // 손해정도비율 (고추에선 평균손해정도비율, 브로콜리에선 작물피해율로 표시)
        BigDecimal uncompensatedRate           // 미보상비율
) {
}
