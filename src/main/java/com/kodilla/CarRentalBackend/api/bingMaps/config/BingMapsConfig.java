package com.kodilla.CarRentalBackend.api.bingMaps.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class BingMapsConfig {
    @Value("${bingMaps.api.url}")
    private String apiUrl;

    @Value("${bingMaps.api.key}")
    private String apiKey;
}
