package com.aivle.agriculture.domain.detail.dto;

import com.aivle.agriculture.domain.detail.enums.InsuredItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter @AllArgsConstructor
public class InsuranceDetailResponse {
    private InsuredItem cropType;
    private String summary;
    private String pdfUrl;
    private InsurancePeriod insurancePeriod;

    @Getter @AllArgsConstructor
    public static class InsurancePeriod {
        private LocalDate start;
        private LocalDate end;
    }
}
