package com.aivle.agriculture.domain.calculate.dto.productioncost;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

/**
 * Group D: (시설재배) 딸기, 토마토, 오이, 참외, 호박, 고추, 파프리카, 국화,
 *       수박, 멜론, 상추, 가지, 배추, 파(대파), 백합, 카네이션,
 *       미나리, 감자, 시금치, 파(쪽파), 무, 쑥갓,
 *       느타리버섯, 새송이버섯, 양송이버섯
 */
public record GroupDProductionCostCalcRequest(
        InsuredItem insuredItem,                       // 보험 품목
        CropType cropType,                             // 작물 종류
        CoverageType coverageType,                     // 보장 종류
        BigDecimal insuredAmount,                      // 보험가입금액
        BigDecimal damagedCultivationArea,             // 피해작물 재배면적
        BigDecimal unitGuaranteedProductionCost,       // 피해작물 단위면적당 보장생산비
        BigDecimal elapsedRate,                        // 경과비율
        BigDecimal damageRate,                         // 피해비율
        BigDecimal damageSeverityRate,                 // 손해정도비율
        BigDecimal uncompensatedRate                   // 미보상비율
) {
}
