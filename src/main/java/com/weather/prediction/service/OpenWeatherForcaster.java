package com.weather.prediction.service;

import com.weather.prediction.clientDTO.WeatherForecastClient;
import com.weather.prediction.serverDTO.WeatherForecast;

public interface OpenWeatherForcaster {
     WeatherForecastClient forcast(String cityName);
}
