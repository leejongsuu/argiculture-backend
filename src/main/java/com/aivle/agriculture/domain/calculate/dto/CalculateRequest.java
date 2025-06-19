package com.aivle.agriculture.domain.calculate.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class CalculateRequest {

    private final InsuredItem insuredItem;
    private final CropType cropType;
    private final CoverageType coverageType;
    private final Map<String, BigDecimal> params = new HashMap<>();

    @JsonCreator
    public CalculateRequest(
            @JsonProperty("insuredItem") InsuredItem insuredItem,
            @JsonProperty("cropType") CropType cropType,
            @JsonProperty("coverageType") CoverageType coverageType
    ) {
        this.insuredItem = insuredItem;
        this.coverageType = coverageType;
        this.cropType = cropType;
    }

    @JsonAnySetter
    public void addParam(String key, Object value) {
        // insuredItem, coverageType, cropType 프로퍼티는 이미 바인딩 됐으니 무시하고,
        // 나머지는 모두 params 맵에 넣는다.
        if ("insuredItem".equals(key) || "coverageType".equals(key) || "cropType".equals(key)) return;

        params.put(key, new BigDecimal(value.toString()));
    }
}
