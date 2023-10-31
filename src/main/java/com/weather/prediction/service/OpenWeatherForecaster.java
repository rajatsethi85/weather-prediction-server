package com.weather.prediction.service;

import com.weather.prediction.clientDTO.WeatherForecastClient;

/**
 * Interface to implement weather forecast service.
 */
public interface OpenWeatherForecaster {

     /**
      * Get weather report by city name.
      *
      * @param cityName Name of the city.
      */
     WeatherForecastClient forecast(String cityName);
}
