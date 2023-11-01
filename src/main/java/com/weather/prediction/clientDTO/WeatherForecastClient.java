package com.weather.prediction.clientDTO;

import com.weather.prediction.serverDTO.WeatherForecast;
import com.weather.prediction.serverDTO.WeatherForecastDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for transferring the useful data to client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastClient {
    /**
     * Holds the message returned by weather API.
     */
    private String message;

    /**
     * Holds the details related to weather forecast returned by API.
     */
    private List<List<WeatherForecastDetails>> WeatherForecastDetails;

    /**
     * Holds the details related to city for which we are fetching the weather report..
     */
    private WeatherForecast.City city;
}
