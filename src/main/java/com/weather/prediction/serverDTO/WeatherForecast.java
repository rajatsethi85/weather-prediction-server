package com.weather.prediction.serverDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO to map values returned by weather API into java objects.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast {
    /**
     * Cod of the reponse. Whether it's success 200 or others.
     */
    private String cod;

    /**
     * Message returned by API.
     */
    private String message;

    /**
     * List of details related to weather forecast.
     */
    private List<WeatherForecastDetails> list;
    private City city;

    /**
     * Static class to store city details returned by weather API.
     */
    @Data
    public static class City {
        /**
         * Name of the city.
         */
        private String name;

        /**
         * Name of the country.
         */
        private String country;
    }
}
