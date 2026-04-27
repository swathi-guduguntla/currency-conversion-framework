package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {
    public static double getRate(String from, String to) {

        String url = "https://open.er-api.com/v6/latest/" + from;

        Response response = RestAssured.get(url);
        System.out.println("API Response: " + response.asString());

        String result = response.jsonPath().getString("result");
        if (!"success".equalsIgnoreCase(result)) {
            throw new RuntimeException(
                    "API failed for currency pair: " + from + " -> " + to +
                            " | Response: " + response.asString()
            );
        }
        Double rate = response.jsonPath().getDouble("rates." + to);

        if (rate == null) {
            throw new RuntimeException("Rate not found for: " + to);
        }
        return rate;
    }
}
