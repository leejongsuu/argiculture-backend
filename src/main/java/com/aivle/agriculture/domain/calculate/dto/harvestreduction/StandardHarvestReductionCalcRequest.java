package com.aivle.agriculture.domain.calculate.dto.harvestreduction;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

/**
 *  일반 그룹: 고구마, 양파, 수박, 참깨, 콩,
 *             팥, 양배추, 마늘, 차, 단호박,
 *             밤, 자두, 매실, 오미자, 유자, 참다래,
 *             호두, 살구, 감귤(만감류), 대추, 포도,
 *             벼, 밀, 보리, 귀리
 */
public record StandardHarvestReductionCalcRequest(
        InsuredItem insuredItem,               // 보험 품목
        CropType cropType,                     // 작물 종류
        CoverageType coverageType,             // 보장 종류
        BigDecimal insuredAmount,              // 보험가입금액
        BigDecimal deductibleRate,             // 자기부담비율
        BigDecimal averageYield,               // 평년수확량
        BigDecimal actualYield,                // 수확량
        BigDecimal uncompensatedReductionQuantity // 미보상감수량
) {
}
