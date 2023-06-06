package com.kodilla.CarRentalBackend.api.openWeather;

import com.kodilla.CarRentalBackend.api.openWeather.client.OpenWeatherClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OpenWeatherClientTest {

    @Value("${openWeather.api.url}")
    private String apiUrl;

    @Value("${openWeather.api.key}")
    private String apiKey;

    @Autowired
    private OpenWeatherClient openWeatherClient =  new OpenWeatherClient(apiUrl, apiKey);


    @Test
    public void testGetWeatherInfo() throws IOException {
        String city = "Berlin";
        LocalDate date = LocalDate.of(2023,6,6);

        String weatherInfo = openWeatherClient.getWeatherInfo(city, date);

        assertEquals("clear sky", weatherInfo);
    }

    @Test
    public void testParseWeatherInfo() {
        String response = "{\"weather\":[{\"description\":\"Sunny\"}]}";

        String weatherInfo = openWeatherClient.parseWeatherInfo(response);

        assertEquals("Sunny", weatherInfo);
    }
}

