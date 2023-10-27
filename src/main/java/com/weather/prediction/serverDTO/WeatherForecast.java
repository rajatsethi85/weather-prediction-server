package com.weather.prediction.serverDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast {
    private String cod;
    private String message;
    private List<WeatherForecastDetails> list;
    private City city;

    @Data
    public static class City {
        private String name;
        private String country;
    }
}
