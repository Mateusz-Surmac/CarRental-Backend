package com.kodilla.CarRentalBackend.api.openWeather.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class OpenWeatherConfig {
    @Value("${openWeather.api.url}")
    private String apiUrl;

    @Value("${openWeather.api.key}")
    private String apiKey;
}
