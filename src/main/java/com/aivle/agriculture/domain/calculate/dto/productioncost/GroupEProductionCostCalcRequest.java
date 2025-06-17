package com.aivle.agriculture.domain.calculate.dto.productioncost;


import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

/**
 * Group E: 원목재배 표고버섯, 부추
 */
public record GroupEProductionCostCalcRequest(
        InsuredItem insuredItem,                       // 보험 품목
        CropType cropType,                             // 작물 종류
        CoverageType coverageType,                     // 보장 종류
        BigDecimal insuredAmount,                      // 보험가입금액
        BigDecimal damagedCultivationArea,             // 피해작물 재배면적
        BigDecimal unitGuaranteedProductionCost,       // 피해작물 단위면적당 보장생산비
        BigDecimal damageRate,                         // 피해비율
        BigDecimal damageSeverityRate,                 // 손해정도비율
        BigDecimal uncompensatedRate                   // 미보상비율
) {
}
