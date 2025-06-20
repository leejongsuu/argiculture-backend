package com.aivle.agriculture.domain.calculate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InsuredItem {
    FIELD_CROPS("밭작물"),             // 밭작물
    FRUIT_CROPS("과수작물"),             // 과수작물
    HORTICULTURAL_FACILITY("원예시설"),  // 원예시설
    PADDY_CEREALS("벼맥류"),           // 벼맥류
    MUSHROOMS("버섯");                // 버섯

    private final String koreanName;

    InsuredItem(String koreanName) {
        this.koreanName = koreanName;
    }

    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    @JsonCreator
    public static InsuredItem fromKoreanName(String koreanName) {
        for (InsuredItem item : InsuredItem.values()) {
            if (item.koreanName.equals(koreanName)) {
                return item;
            }
        }
        throw new IllegalArgumentException("해당 한글명에 해당하는 보험 항목이 없습니다: " + koreanName);
    }
}
