package com.aivle.agriculture.domain.mainpage.dto;
import java.time.LocalDate;

public class InsurancePeriodCardDto {
    private String cropType;
    private LocalDate startDate;
    private LocalDate endDate;

    public InsurancePeriodCardDto(String cropType, LocalDate startDate, LocalDate endDate) {
        this.cropType = cropType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCropType() { return cropType; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
}
