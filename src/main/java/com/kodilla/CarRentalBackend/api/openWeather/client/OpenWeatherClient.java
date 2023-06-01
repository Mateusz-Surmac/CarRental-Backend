package com.kodilla.CarRentalBackend.api.openWeather.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class OpenWeatherClient {

    private final String apiUrl;
    private final String apiKey;

    public OpenWeatherClient(@Value("${openWeather.api.url}") String apiUrl,
                             @Value("${openWeather.api.key}") String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public String getWeatherInfo(String city, LocalDate date) throws IOException {
        long unixTimestamp = date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);

        String apiUrl = this.apiUrl + "?q=" + city + "&dt=" + unixTimestamp + "&appid=" + apiKey;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();

            return parseWeatherInfo(response);
        } else {
            throw new IOException();
        }
    }

    public String parseWeatherInfo(String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray weatherArray = jsonObject.getJSONArray("list");

        JSONObject weatherObj = weatherArray.getJSONObject(0);
        JSONArray weatherDetails = weatherObj.getJSONArray("weather");
        JSONObject weatherDetailObj = weatherDetails.getJSONObject(0);

        return weatherDetailObj.getString("description");
    }
}
