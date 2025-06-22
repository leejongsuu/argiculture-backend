package com.aivle.agriculture.domain.mainpage.dto;

public class ForecastDetailDto {
    private String hour;
    private double temp;
    private String weather;
    private double rain;

    public ForecastDetailDto(String hour, double temp, String weather, double rain) {
        this.hour = hour;
        this.temp = temp;
        this.weather = weather;
        this.rain = rain;
    }

    public String getHour() { return hour; }
    public double getTemp() { return temp; }
    public String getWeather() { return weather; }
    public double getRain() { return rain; }
}

