package com.weather.prediction.serverDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO to hold the data related to weather forecast.
 */
@Builder
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastDetails {
    /**
     * Holds the data of key "main" returned by API.
     */
    private Main main;

    /**
     * Holds the list of weather.
     */
    private List<Weather> weather;

    /**
     * Holds the info related to clouds returned by API.
     */
    private Clouds clouds;

    /**
     * Holds the info related to wind returned by API.
     */
    private Wind wind;

    /**
     * Holds the info related to date time returned by API.
     */
    @JsonProperty(value = "dt")
    private String dateTime;

    /**
     * Holds the info related to date returned by API.
     */
    private LocalDate date;

    /**
     * DTO to store data of main field.
     */
    @Data
    public static class Main {

        /**
         * Holds the info related to temperature returned by API.
         */
        private double temp;

        /**
         * Holds the info related to feels_like returned by API.
         */
        private double feels_like;

        /**
         * Holds the info related to min temperature returned by API.
         */
        private double temp_min;

        /**
         * Holds the info related to max temperature returned by API.
         */
        private double temp_max;
    }

    /**
     * Class to store the data related to weather.
     */
    @Data
    public static class Weather {

        /**
         * Holds the info related to type of weather returned by API.
         */
        @JsonProperty(value = "main")
        private String type;

        /**
         * Holds the description returned by API.
         */
        private String description;

        /**
         * Holds the info related to rain returned by API.
         */
        private boolean raining;

        /**
         * Holds the info related to isTooHot returned by API.
         */
        private boolean isTooHot;

        /**
         * Holds the info related to highWinds returned by API.
         */
        private boolean highWinds;

        /**
         * Holds the info related to thunderstorm returned by API.
         */
        private boolean thunderstorm;

        /**
         * Holds the info related to thunderstorm returned by API.
         */
        private boolean rainPredicted;
    }

    /**
     * Static class to store information related to clouds.
     */
    @Data
    public static class Clouds {
        @JsonProperty(value = "all")
        private int rainPossibility;
    }

    /**
     * Static class to store information related to wind.
     */
    @Data
    public static class Wind {
        private int speed;
    }
}
