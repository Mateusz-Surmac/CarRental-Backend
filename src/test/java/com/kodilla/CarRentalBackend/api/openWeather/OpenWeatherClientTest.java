package com.kodilla.CarRentalBackend.api.openWeather;

import com.kodilla.CarRentalBackend.api.openWeather.client.OpenWeatherClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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

