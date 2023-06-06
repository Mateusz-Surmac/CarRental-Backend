package com.kodilla.CarRentalBackend.api.bingMaps;

import com.kodilla.CarRentalBackend.api.bingMaps.client.BingMapsClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class BingMapsClientTest {


    @Value("${bingMaps.api.url}")
    private String apiUrl;

    @Value("${bingMaps.api.key}")
    private String apiKey;

    @Autowired
    private BingMapsClient bingMapsClient = new BingMapsClient(apiUrl, apiKey);


    @Test
    public void testGetDistance() {

        String startLocation = "Berlin";
        String endLocation = "Paris";

        double distance = bingMapsClient.getDistance(startLocation, endLocation);

        assertEquals(1134.073, distance);
    }

    @Test
    public void testParseDistanceFromResponse() {
        String responseJson = "{\"resourceSets\":[{\"resources\":[{\"travelDistance\":2789.0}]}]}";
        double expectedDistance = 2789.0;

        double distance = bingMapsClient.parseDistanceFromResponse(responseJson);

        assertEquals(expectedDistance, distance);
    }

}
