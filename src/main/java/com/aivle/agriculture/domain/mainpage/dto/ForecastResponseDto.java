package com.aivle.agriculture.domain.mainpage.dto;

import java.util.List;

public class ForecastResponseDto {
    private String city;
    private List<ForecastDetailDto> today;
    private List<ForecastDetailDto> tomorrow;

    public ForecastResponseDto(String city, List<ForecastDetailDto> today, List<ForecastDetailDto> tomorrow) {
        this.city = city;
        this.today = today;
        this.tomorrow = tomorrow;
    }

    public String getCity() { return city; }
    public List<ForecastDetailDto> getToday() { return today; }
    public List<ForecastDetailDto> getTomorrow() { return tomorrow; }
}