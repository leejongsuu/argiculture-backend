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

    private static final Set<CropType> GROUP_A = Set.of(
            CropType.STRAWBERRY,     // 딸기
            CropType.TOMATO,         // 토마토
            CropType.CUCUMBER,       // 오이
            CropType.KOREAN_MELON,   // 참외
            CropType.PUMPKIN,        // 호박
            CropType.PEPPER,         // 고추
            CropType.PAPRIKA,        // 파프리카
            CropType.CHRYSANTHEMUM,  // 국화
            CropType.WATERMELON,     // 수박
            CropType.MELON,          // 멜론
            CropType.LETTUCE,        // 상추
            CropType.EGGPLANT,       // 가지
            CropType.CABBAGE,        // 배추
            CropType.GREEN_ONION,    // 대파
            CropType.LILY,           // 백합
            CropType.CARNATION,      // 카네이션
            CropType.WATER_PARSLEY,  // 미나리
            CropType.POTATO,         // 감자
            CropType.SPINACH,        // 시금치
            CropType.SCALLION,       // 쪽파
            CropType.RADISH,         // 무
            CropType.CROWN_DAISY,    // 쑥갓
            CropType.OYSTER_MUSHROOM,        // 느타리버섯
            CropType.KING_OYSTER_MUSHROOM,   // 새송이버섯
            CropType.BUTTON_MUSHROOM         // 양송이버섯
    );

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
        if (!GROUP_A.contains(cropType)) {
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
