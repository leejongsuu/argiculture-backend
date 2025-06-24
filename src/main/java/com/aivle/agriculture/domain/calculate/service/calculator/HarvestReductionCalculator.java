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
public class HarvestReductionCalculator implements Calculator {

    private static final Set<CropType> STANDARD = Set.of(
            CropType.SWEET_POTATO,  // 고구마
            CropType.ONION,         // 양파
            CropType.WATERMELON,    // 수박
            CropType.SESAME,        // 참깨
            CropType.SOYBEAN,       // 콩
            CropType.RED_BEAN,      // 팥
            CropType.NAPA_CABBAGE,  // 양배추
            CropType.GARLIC,        // 마늘
            CropType.TEA,           // 차
            CropType.AUTUMN_SQUASH, // 단호박
            CropType.CHESTNUT,      // 밤
            CropType.PLUM,          // 자두
            CropType.MAESIL,        // 매실
            CropType.OMIJA,         // 오미자
            CropType.YUZU,          // 유자
            CropType.KIWI,          // 참다래
            CropType.WALNUT,        // 호두
            CropType.APRICOT,       // 살구
            CropType.CITRUS,        // 감귤(만감류)
            CropType.JUJUBE,        // 대추
            CropType.GRAPE,         // 포도
            CropType.RICE,          // 벼
            CropType.WHEAT,         // 밀
            CropType.BARLEY,        // 보리
            CropType.OAT            // 귀리
    );

    private static final Set<CropType> DISEASE = Set.of(
            CropType.POTATO,        // 감자(봄재배, 고랭지재배, 가을재배)
            CropType.PEACH          // 복숭아
    );

    private static final Set<CropType> CORN = Set.of(
            CropType.CORN           // 옥수수
    );

    @Override
    public BigDecimal calculate(CalculateRequest request) {

        CropType cropType = request.getCropType();

        if (STANDARD.contains(cropType) || DISEASE.contains(cropType)) {
            return calculateStandardOrDiseaseHarvestReduction(request.getCropType(), request.getParams());
        } else if (CORN.contains(cropType)) {
            return calculateCornHarvestReduction(request.getParams());
        } else {
            throw new IllegalArgumentException("Unknown crop type: " + cropType);
        }
    }

    // 보험가입금액 × (피해율 - 자기부담비율)
    // 피해율 = (평년수확량 - 수확량 - 미보상감수량) ÷ 평년수확량 -> standard
    // 피해율 = {(평년수확량 - 수확량 - 미보상감수량) + 병충해감수량} ÷ 평년수확량 -> disease
    private BigDecimal calculateStandardOrDiseaseHarvestReduction(CropType cropType, Map<String, BigDecimal> params) {
        BigDecimal insuredAmount = MapUtils.getRequired(params, "insuredAmount"); // 보험가입금액
        BigDecimal deductibleRate = MapUtils.getRequired(params, "deductibleRate"); // 자기부담비율
        BigDecimal averageYield = MapUtils.getRequired(params, "averageYield"); // 평년수확량
        BigDecimal actualYield = MapUtils.getRequired(params, "actualYield"); // 수확량
        BigDecimal uncompensatedReductionQuantity = MapUtils.getRequired(params, "uncompensatedReductionQuantity"); // 미보상감수량

        BigDecimal damageRate = averageYield.subtract(actualYield).subtract(uncompensatedReductionQuantity);
        if (DISEASE.contains(cropType)) {
            BigDecimal diseasePestReductionQuantity = MapUtils.getRequired(params, "diseasePestReductionQuantity"); // 병충해감수량

            damageRate = (damageRate
                    .divide(diseasePestReductionQuantity,2, RoundingMode.HALF_UP))
                    .divide(averageYield,2, RoundingMode.HALF_UP);
        } else {
            damageRate = damageRate
                    .divide(averageYield,2, RoundingMode.HALF_UP);
        }

        return insuredAmount.multiply(damageRate.subtract(deductibleRate)).setScale(0, RoundingMode.HALF_UP);
    }

    // MIN[보험가입금액, 손해액] - 자기부담금
    // 손해액 = 피해수확량 * 가입가격
    // 자기부담금 = 보험가입금액 * 자기부담비율
    private BigDecimal calculateCornHarvestReduction(Map<String, BigDecimal> params) {
        BigDecimal insuredAmount = MapUtils.getRequired(params, "insuredAmount"); // 보험가입금액
        BigDecimal deductibleRate = MapUtils.getRequired(params, "deductibleRate"); // 자기부담비율
        BigDecimal damageHarvestQuantity = MapUtils.getRequired(params, "damageHarvestQuantity"); // 피해수확량
        BigDecimal premiumPaid = MapUtils.getRequired(params, "premiumPaid"); // 가입가격

        return insuredAmount.min(damageHarvestQuantity.multiply(premiumPaid))
                .subtract(insuredAmount.multiply(deductibleRate))
                .setScale(0, RoundingMode.HALF_UP);
    }
}
