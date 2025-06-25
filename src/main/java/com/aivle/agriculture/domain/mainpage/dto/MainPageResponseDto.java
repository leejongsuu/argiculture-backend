package com.aivle.agriculture.domain.mainpage.dto;

import java.util.List;

public class MainPageResponseDto {
    private List<InsurancePeriodCardDto> insuranceCards;
    private ForecastResponseDto forecast;

    public MainPageResponseDto(List<InsurancePeriodCardDto> insuranceCards, ForecastResponseDto forecast) {
        this.insuranceCards = insuranceCards;
        this.forecast = forecast;
    }

    public List<InsurancePeriodCardDto> getInsuranceCards() { return insuranceCards; }
    public ForecastResponseDto getForecast() { return forecast; }
}
