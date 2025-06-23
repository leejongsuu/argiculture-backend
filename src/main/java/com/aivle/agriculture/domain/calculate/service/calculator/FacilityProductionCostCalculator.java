package com.aivle.agriculture.domain.calculate.service.calculator;

import com.aivle.agriculture.domain.calculate.dto.CalculateRequest;
import com.aivle.agriculture.domain.calculate.dto.CropType;
import com.aivle.agriculture.domain.calculate.utils.MapUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

@Component
public class FacilityProductionCostCalculator implements Calculator {

    private static final Set<CropType> GROUP_B = Set.of(
            CropType.LOG_SHIITAKE,   // 원목재배 표고버섯
            CropType.CHIVE           // 부추
    );

    @Override
    public BigDecimal calculate(CalculateRequest request) {
        CropType cropType = request.getCropType();
        Map<String, BigDecimal> params = request.getParams();
        BigDecimal insuredAmount = MapUtils.getRequired(params, "insuredAmount"); // 보험가입금액
        BigDecimal damagedCultivationArea = MapUtils.getRequired(params, "damagedCultivationArea"); // 피해작물 재배면적
        BigDecimal unitGuaranteedProductionCost = MapUtils.getRequired(params, "unitGuaranteedProductionCost"); // 피해작물 단위면적당 보장생산비
        BigDecimal damageRate = MapUtils.getRequired(params, "damageRate"); // 피해비율
        BigDecimal damageSeverityRate = MapUtils.getRequired(params, "damageSeverityRate"); // 손해정도비율
        BigDecimal uncompensatedRate = MapUtils.getRequired(params, "uncompensatedRate"); // 미보상비율

        // 피해작물 재배면적 * 피해작물 단위면적당 보장생산비 * 피해비율 * 손해정도비율 * (1 - 미보상비율)
        BigDecimal insurance = damagedCultivationArea.multiply(unitGuaranteedProductionCost)
                .multiply(damageRate).multiply(damageSeverityRate)
                .multiply(BigDecimal.ONE.subtract(uncompensatedRate));

        // 피해작물 재배면적 * 피해작물 단위면적당 보장생산비 * 경과비율 * 피해비율 * 손해정도비율 * (1 - 미보상비율)
        if (!GROUP_B.contains(cropType)) {
            BigDecimal elapsedRate = MapUtils.getRequired(params, "elapsedRate"); // 경과비율

            insurance = insurance.multiply(elapsedRate);
        }

        // 단, 부추는 (* 70%)
        if (cropType == CropType.CHIVE) {
            insurance = insurance.multiply(new BigDecimal("0.7"));
        }

        // 보험가입금액 < 피해작물재배면적 * 피해작물 단위면적당 보장생산비 이면
        // 위의 생산비보장보험금 * (보험가입금액 / (피해작물 재배면적 * 피해작물 단위면적당 보장생산비))
        if (insuredAmount.compareTo(damagedCultivationArea.multiply(unitGuaranteedProductionCost)) < 0) {
            insurance = insurance.multiply(insuredAmount
                    .divide(damagedCultivationArea.multiply(unitGuaranteedProductionCost),2, RoundingMode.HALF_UP));
        }

        return insurance.setScale(0, RoundingMode.HALF_UP);
    }
}
