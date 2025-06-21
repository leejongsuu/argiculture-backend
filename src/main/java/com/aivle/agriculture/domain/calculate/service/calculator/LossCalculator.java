package com.aivle.agriculture.domain.calculate.service.calculator;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.utils.MapUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Component
public class LossCalculator implements Calculator {

    @Override
    // 보험가입금액 * (피해율 - 자기부담비율)
    public BigDecimal calculate(CalculateRequest request) {
        CropType cropType = request.getCropType();
        Map<String, BigDecimal> params = request.getParams();

        BigDecimal insuredAmount = MapUtils.getRequired(params, "insuredAmount"); // 보험가입금액
        BigDecimal deductibleRate = MapUtils.getRequired(params, "deductibleRate"); // 자기부담비율

        BigDecimal insurance;
        if (cropType == CropType.GINSENG) {
            insurance = calculateGinsengDamageRate(params);
        } else if (cropType == CropType.CITRUS) {
            insurance = calculateCitrusDamageRate(params);
        } else if (cropType == CropType.MULBERRY) {
            insurance = calculateMulberryDamageRate(params);
        } else if (cropType == CropType.DUREUP) {
            insurance = calculateDureupDamageRate(params);
        } else if (cropType == CropType.BLUEBERRY) {
            insurance = calculateBlueberryDamageRate(params);
        } else if (cropType == CropType.BOKBUNJA) {
            insurance = calculateBokbunjaDamageRate(params);
        } else if (cropType == CropType.FIG) {
            insurance = calculateFigDamageRate(params);
        } else {
            throw new IllegalArgumentException("Unknown crop type: " + cropType);
        }

        return (insurance.subtract(deductibleRate)).multiply(insuredAmount).setScale(0, RoundingMode.HALF_UP);
    }

    // (1 - 수확량 ÷ 연근별기준수확량) × (피해면적 ÷ 재배면적)
    private BigDecimal calculateGinsengDamageRate(Map<String, BigDecimal> params) {
        BigDecimal actualYield = MapUtils.getRequired(params, "actualYield"); // 수확량
        BigDecimal standardYieldByCultivationYear = MapUtils.getRequired(params, "standardYieldByCultivationYear"); // 연근별기준수확량
        BigDecimal damagedArea = MapUtils.getRequired(params, "damagedArea"); // 피해면적
        BigDecimal cultivationArea = MapUtils.getRequired(params, "cultivationArea"); // 재배면적

        return (BigDecimal.ONE.subtract(actualYield.divide(standardYieldByCultivationYear, 2, RoundingMode.HALF_UP)))
                .multiply(damagedArea.divide(cultivationArea, 2, RoundingMode.HALF_UP));
    }

    // {(등급 내 피해 과실수 + 등급 외 피해 과실수 * 50%) / 기준과실수} * (1 – 미보상비율)
    private BigDecimal calculateCitrusDamageRate(Map<String, BigDecimal> params) {
        BigDecimal withinGradeDamageFruitCount = MapUtils.getRequired(params, "withinGradeDamageFruitCount"); // 등급 내 피해 과실수
        BigDecimal outOfGradeDamageFruitCount = MapUtils.getRequired(params, "outOfGradeDamageFruitCount"); // 등급 외 피해 과실수
        BigDecimal standardFruitCount = MapUtils.getRequired(params, "standardFruitCount"); // 기준과실수
        BigDecimal uncompensatedRate = MapUtils.getRequired(params, "uncompensatedRate"); // 미보상비율

        return (withinGradeDamageFruitCount.add(outOfGradeDamageFruitCount.multiply(new BigDecimal("0.5"))).divide(standardFruitCount, 2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.ONE.subtract(uncompensatedRate));
    }

    // (평년결실수 - 조사결실수 - 미보상감수결실수) / 평년결실수
    private BigDecimal calculateMulberryDamageRate(Map<String, BigDecimal> params) {
        BigDecimal averageFruitCount = MapUtils.getRequired(params, "averageFruitCount"); // 평년결실수
        BigDecimal surveyedFruitCount = MapUtils.getRequired(params, "surveyedFruitCount"); // 조사결실수
        BigDecimal uncompensatedReductionFruitCount = MapUtils.getRequired(params, "uncompensatedReductionFruitCount"); // 미보상결실수

        return (averageFruitCount.subtract(surveyedFruitCount).subtract(uncompensatedReductionFruitCount))
                .divide(averageFruitCount, 2, RoundingMode.HALF_UP);
    }

    // (피해 정아지 수 ÷ 총 정아지 수) × (1 - 미보상비율)
    private BigDecimal calculateDureupDamageRate(Map<String, BigDecimal> params) {
        BigDecimal damagedFruitletCount = MapUtils.getRequired(params, "damagedFruitletCount"); // 피해 정아지 수
        BigDecimal totalFruitletCount = MapUtils.getRequired(params, "totalFruitletCount"); // 총 정아지 수
        BigDecimal uncompensatedReductionFruitCount = MapUtils.getRequired(params, "uncompensatedReductionFruitCount"); // 미보상비율

        return (damagedFruitletCount.divide(totalFruitletCount, 2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.ONE.subtract(uncompensatedReductionFruitCount));
    }

    // 최종 꽃 피해율 + {(1-최종 꽃 피해율) × 과실손해피해율 × (1-미보상비율)}
    private BigDecimal calculateBlueberryDamageRate(Map<String, BigDecimal> params) {
        BigDecimal finalFlowerDamageRate = MapUtils.getRequired(params, "finalFlowerDamageRate"); // 최종 꽃 피해율
        BigDecimal fruitDamageRate = MapUtils.getRequired(params, "fruitDamageRate"); // 과실 손해 피해율
        BigDecimal uncompensatedReductionFruitCount = MapUtils.getRequired(params, "uncompensatedReductionFruitCount"); // 미보상비율

        return finalFlowerDamageRate.add((BigDecimal.ONE.subtract(uncompensatedReductionFruitCount))
                .multiply(fruitDamageRate)
                .multiply(BigDecimal.ONE.subtract(uncompensatedReductionFruitCount)));
    }

    // 고사결과모지수 ÷ 평년결과모지수
    private BigDecimal calculateBokbunjaDamageRate(Map<String, BigDecimal> params) {
        BigDecimal mortalityFruitIndex = MapUtils.getRequired(params, "mortalityFruitIndex"); // 고사결과모지수
        BigDecimal averageFruitIndex = MapUtils.getRequired(params, "averageFruitIndex"); // 평년결과모지수

        return mortalityFruitIndex.divide(averageFruitIndex, 2, RoundingMode.HALF_UP);
    }


    // 피해율 (이듬해 7월 31일 이전 사고): (평년수확량 – 수확량 – 미보상감수량) ÷ 평년수확량
    // 피해율 (이듬해 8월 1일 이후 사고): (1 – 수확전사고 피해율) × 경과비율 × 결과지 피해율
    private BigDecimal calculateFigDamageRate(Map<String, BigDecimal> params) {
        BigDecimal accidentOccurrenceMonth = MapUtils.getRequired(params, "accidentOccurrenceMonth"); // 사고 발생 월

        if (accidentOccurrenceMonth.compareTo(new BigDecimal("7")) <= 0) {
            BigDecimal averageYield = MapUtils.getRequired(params, "averageYield"); // 평년수확량
            BigDecimal actualYield = MapUtils.getRequired(params, "actualYield"); // 수확량
            BigDecimal uncompensatedReductionQuantity = MapUtils.getRequired(params, "uncompensatedReductionQuantity"); // 미보상감수량

            return (averageYield.subtract(actualYield).subtract(uncompensatedReductionQuantity)).divide(averageYield, 2, RoundingMode.HALF_UP);
        } else {
            BigDecimal preHarvestAccidentDamageRate = MapUtils.getRequired(params, "preHarvestAccidentDamageRate"); // 수확전사고 피해율
            BigDecimal elapsedRate = MapUtils.getRequired(params, "elapsedRate"); // 경과비율
            BigDecimal postHarvestDamageRate = MapUtils.getRequired(params, "postHarvestDamageRate"); // 결과지 피해율

            return BigDecimal.ONE.subtract(preHarvestAccidentDamageRate).multiply(elapsedRate).multiply(postHarvestDamageRate);
        }
    }
}
