package com.weather.prediction.clientDTO;

import com.weather.prediction.serverDTO.WeatherForecast;
import com.weather.prediction.serverDTO.WeatherForecastDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeatherForecastClient {
    private String message;
    private List<List<WeatherForecastDetails>> WeatherForecastDetails;
    private WeatherForecast.City city;
}
