package com.aivle.agriculture.domain.calculate.dto.harvestreduction;

import com.aivle.agriculture.domain.calculate.dto.CoverageType;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.dto.InsuredItem;

import java.math.BigDecimal;

/**
 * 병충해 그룹: 감자(봄재배, 고랭지재배, 가을재배), 복숭아
 */
public record DiseaseHarvestReductionCalcRequest(
        InsuredItem insuredItem,               // 보험 품목
        CropType cropType,                     // 작물 종류
        CoverageType coverageType,             // 보장 종류
        BigDecimal insuredAmount,              // 보험가입금액
        BigDecimal deductibleRate,             // 자기부담비율
        BigDecimal averageYield,               // 평년수확량
        BigDecimal actualYield,                // 수확량
        BigDecimal uncompensatedReductionQuantity, // 미보상감수량
        BigDecimal diseasePestReductionQuantity    // 병충해감수량
) {
}
