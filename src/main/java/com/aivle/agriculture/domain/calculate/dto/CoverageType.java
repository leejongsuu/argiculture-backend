package com.aivle.agriculture.domain.calculate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CoverageType {
    
    HARVEST_REDUCTION("수확감소보장"),   // 수확감소보장
    PRODUCTION_COST("생산비보장"),     // 생산비보장
    LOSS("손해보장");       // 손해보장

    private final String koreanName;

    CoverageType(String koreanName) {
        this.koreanName = koreanName;
    }

    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    @JsonCreator
    public static CoverageType fromKoreanName(String koreanName) {
        for (CoverageType type : CoverageType.values()) {
            if (type.koreanName.equals(koreanName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("해당 한글명에 해당하는 보장 타입이 없습니다: " + koreanName);
    }
}