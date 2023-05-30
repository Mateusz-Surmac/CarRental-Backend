package com.kodilla.CarRentalBackend.api.bingMaps.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BingMapsClient {

    private final String apiUrl;
    private final String apiKey;
    private final WebClient webClient;

    public BingMapsClient(@Value("${bingMaps.api.url}") String apiUrl,
                          @Value("${bingMaps.api.key}") String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.webClient = WebClient.builder().build();
    }

    public double getDistance(String startLocation, String endLocation) {
        String url = apiUrl + "/REST/V1/Routes/Driving?o=json&wp.0=" + startLocation + "&wp.1=" + endLocation + "&avoid=minimizeTolls&key=" + apiKey;

        String responseJson = webClient.get()
                .uri(url)
                .header(HttpHeaders.ACCEPT, "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseDistanceFromResponse(responseJson);
    }

    public double parseDistanceFromResponse(String responseJson) {
        try {
            JSONObject jsonResponse = new JSONObject(responseJson);
            JSONArray resourceSetsArray = jsonResponse.getJSONArray("resourceSets");

            if (resourceSetsArray.length() > 0) {
                JSONObject resourceSet = resourceSetsArray.getJSONObject(0);
                JSONArray resourcesArray = resourceSet.getJSONArray("resources");

                if (resourcesArray.length() > 0) {
                    JSONObject resource = resourcesArray.getJSONObject(0);
                    double travelDistance = resource.getDouble("travelDistance");

                    return travelDistance;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
