package com.aivle.agriculture.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebClientConfig implements WebMvcConfigurer {

    @Bean
    public WebClient webClient(WebClient.Builder builder, ObjectMapper om) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(cc -> {
                    cc.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(om));
                    cc.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(om));
                }).build();

        return builder
                .exchangeStrategies(strategies)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
} 