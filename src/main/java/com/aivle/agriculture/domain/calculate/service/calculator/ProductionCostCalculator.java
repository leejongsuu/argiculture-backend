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
public class ProductionCostCalculator implements Calculator {

    private static final Set<CropType> GROUP_A = Set.of(
            CropType.PEPPER,         // 고추
            CropType.BROCCOLI        // 브로콜리
    );

    private static final Set<CropType> GROUP_B = Set.of(
            CropType.CARROT,         // 당근
            CropType.CABBAGE,        // 배추
            CropType.RADISH,         // 무
            CropType.GREEN_ONION,    // 파
            CropType.SPINACH,        // 시금치
            CropType.ICEBERG_LETTUCE         // 양상추
    );

    private static final Set<CropType> GROUP_C = Set.of(
            CropType.BUCKWHEAT       // 메밀
    );

    @Override
    // 보험가입금액 * (피해율 - 자기부담율)
    public BigDecimal calculate(CalculateRequest request) {
        CropType cropType = request.getCropType();
        Map<String, BigDecimal> params = request.getParams();
        BigDecimal insuredAmount = MapUtils.getRequired(params, "insuredAmount"); // 보험가입금액
        BigDecimal deductibleRate = MapUtils.getRequired(params, "deductibleRate"); // 자기부담비율
        BigDecimal areaDamageRate = MapUtils.getRequired(params, "areaDamageRate"); // 면적피해율
        BigDecimal uncompensatedRate = MapUtils.getRequired(params, "uncompensatedRate"); // 미보상비율

        BigDecimal insurance = areaDamageRate.multiply(BigDecimal.ONE.subtract(uncompensatedRate));
        if (GROUP_A.contains(cropType)) {
            BigDecimal elapsedRate = MapUtils.getRequired(params, "elapsedRate"); // 경과비율
            BigDecimal averageDamageSeverityRate = MapUtils.getRequired(params, "averageDamageSeverityRate"); // 평균손해정도비율 혹은 작물피해율

            insurance = insurance.multiply(averageDamageSeverityRate).multiply(elapsedRate);
        } else if (GROUP_B.contains(cropType)) {
            BigDecimal averageDamageSeverityRate = MapUtils.getRequired(params, "averageDamageSeverityRate"); // 평균손해정도비율 혹은 작물피해율

            insurance = insurance.multiply(averageDamageSeverityRate);
        } else if (!GROUP_C.contains(cropType)) {
            throw new IllegalArgumentException("Unknown crop type: " + cropType);
        }

        return insurance.subtract(deductibleRate).multiply(insuredAmount).setScale(0, RoundingMode.HALF_UP);
    }
}
