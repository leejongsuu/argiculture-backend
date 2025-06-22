package com.aivle.agriculture.domain.mainpage.service;

import com.aivle.agriculture.domain.detail.entity.InsuranceProduct;
import com.aivle.agriculture.domain.detail.repository.InsuranceProductRepository;
import com.aivle.agriculture.domain.mainpage.dto.ForecastDetailDto;
import com.aivle.agriculture.domain.mainpage.dto.ForecastResponseDto;
import com.aivle.agriculture.domain.mainpage.dto.InsurancePeriodCardDto;
import com.aivle.agriculture.domain.mainpage.dto.MainPageResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MainPageService {
    @Value("${weather.api.key}")
    private String apiKey;
    private final InsuranceProductRepository insuranceProductRepository;

    private final String baseUrl = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&lang=kr&units=metric";

    public MainPageResponseDto getMainPage() {
        // 1. 보험카드 정보 5개
        List<InsuranceProduct> products = insuranceProductRepository.findTop5ByOrderByIdAsc();
        List<InsurancePeriodCardDto> insuranceCards = products.stream()
                .map(p -> new InsurancePeriodCardDto(
                        p.getCropType().getDisplayName(),
                        p.getStartDate(),
                        p.getEndDate()
                )).toList();

        // 2. 날씨 예보
        String city = "Seoul";
        String url = String.format(baseUrl, city, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.getForObject(url, Map.class);

        // 도시명
        Map<String, Object> cityMap = (Map<String, Object>) response.get("city");
        String cityName = cityMap.get("name").toString();

        // 예보 추출
        List<Map<String, Object>> forecastList = (List<Map<String, Object>>) response.get("list");
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate tomorrow = today.plusDays(1);

        List<ForecastDetailDto> todayList = new ArrayList<>();
        List<ForecastDetailDto> tomorrowList = new ArrayList<>();

        for (Map<String, Object> forecast : forecastList) {
            long dt = ((Number) forecast.get("dt")).longValue();
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dt), ZoneId.of("Asia/Seoul"));
            LocalDate date = dateTime.toLocalDate();
            String hour = dateTime.format(DateTimeFormatter.ofPattern("HH:00"));

            Map<String, Object> main = (Map<String, Object>) forecast.get("main");
            double temp = ((Number) main.get("temp")).doubleValue();

            List<Map<String, Object>> weatherArr = (List<Map<String, Object>>) forecast.get("weather");
            String description = weatherArr.get(0).get("description").toString();

            Map<String, Object> rainMap = (Map<String, Object>) forecast.get("rain");
            double rain = (rainMap != null && rainMap.get("3h") != null) ? Double.parseDouble(rainMap.get("3h").toString()) : 0.0;

            ForecastDetailDto dto = new ForecastDetailDto(hour, temp, description, rain);

            if (date.equals(today)) {
                todayList.add(dto);
            } else if (date.equals(tomorrow)) {
                tomorrowList.add(dto);
            }
        }

        ForecastResponseDto forecast = new ForecastResponseDto(cityName, todayList, tomorrowList);

        // 3. 최종 통합 DTO 리턴
        return new MainPageResponseDto(insuranceCards, forecast);
    }
}

