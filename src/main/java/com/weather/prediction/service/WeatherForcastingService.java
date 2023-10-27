package com.weather.prediction.service;

import com.weather.prediction.clientDTO.WeatherForecastClient;
import com.weather.prediction.customExceptions.CityNotFoundException;
import com.weather.prediction.serverDTO.WeatherForecast;
import com.weather.prediction.serverDTO.WeatherForecastDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class WeatherForcastingService implements OpenWeatherForcaster {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String openWeatherMapApiKey;

    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=%s&cnt=35";

    @Override
    public WeatherForecastClient forcast(String cityName) {
        WeatherForecast weatherForecast;
        WeatherForecastClient clientDetails;
        try {
            String url = String.format(WEATHER_URL, cityName, openWeatherMapApiKey);
            weatherForecast = restTemplate.getForObject(url, WeatherForecast.class);
        } catch (Exception e) {
            throw new CityNotFoundException("city not found");
        }
        assert weatherForecast != null;
        weatherForecast.getList().forEach(forecast -> {

            // Convert the epoch timestamp to an Instant
            Instant instant = Instant.ofEpochSecond(Long.parseLong(forecast.getDateTime()));
            ZoneId zoneId = ZoneId.of("UTC");
            // Format the LocalDateTime as a human-readable date and time
            ZonedDateTime zonedDateTime = instant.atZone(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = zonedDateTime.format(formatter);
            forecast.setDateTime(formattedDateTime);
            forecast.setDate(zonedDateTime.toLocalDate());
            forecast.getWeather().forEach(weather -> {
                if (weather.getType().equalsIgnoreCase("clouds") && forecast.getClouds().getRainPossibility() > 60) {
                    weather.setRainPredicted(true);
                }
                if (weather.getType().equalsIgnoreCase("rain") || weather.getType().equalsIgnoreCase("drizzle")) {
                    weather.setRaining(true);
                }
                if (weather.getType().equalsIgnoreCase("thunderstorm")) {
                    weather.setThunderstorm(true);
                }
                if ((forecast.getMain().getTemp()) > 40) {
                    weather.setTooHot(true);
                }
                if (forecast.getWind().getSpeed() > 10) {
                    weather.setHighWinds(true);
                }
            });
        });
        List<List<WeatherForecastDetails>> groupedList = new LinkedList<>(weatherForecast.getList().stream()
                .collect(Collectors.groupingBy(WeatherForecastDetails::getDate)).values());
        Collections.reverse(groupedList);

        AtomicBoolean removeLastElem = new AtomicBoolean(false);
        List<List<WeatherForecastDetails>> filteredList = new LinkedList<>(groupedList);
        groupedList.forEach(outerList -> {
            outerList.forEach(elem -> {
                if (elem.getDate().isAfter(LocalDate.now().plusDays(3))) {
                    removeLastElem.set(true);
                }
            });

        });
        if (removeLastElem.get()) {
            filteredList.remove(filteredList.size() - 1);
        }
        clientDetails = WeatherForecastClient.builder()
                .message(weatherForecast.getMessage()).WeatherForecastDetails(filteredList)
                .city(weatherForecast.getCity()).build();

        return clientDetails;
    }
}
