package com.weather.prediction.serverDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastDetails {
    private Main main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    @JsonProperty(value = "dt")
    private String dateTime;
    private LocalDate date;

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
    }

    @Data
    public static class Weather {
        @JsonProperty(value = "main")
        private String type;
        private String description;
        private boolean raining;
        private boolean isTooHot;
        private boolean highWinds;
        private boolean thunderstorm;
        private boolean rainPredicted;
    }

    @Data
    public static class Clouds {
        @JsonProperty(value = "all")
        private int rainPossibility;
    }

    @Data
    public static class Wind {
        private int speed;
    }
}
