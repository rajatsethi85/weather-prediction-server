package com.weather.prediction.service;

import com.weather.prediction.clientDTO.WeatherForecastClient;
import com.weather.prediction.customExceptions.CityNotFoundException;
import com.weather.prediction.serverDTO.WeatherForecast;
import com.weather.prediction.serverDTO.WeatherForecastDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Service class to fetch data from OpenWeatherForecaster.
 */
@Service
public class WeatherForecastingService implements OpenWeatherForecaster {

    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=%s&cnt=35";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String openWeatherMapApiKey;

    @Override
    public WeatherForecastClient forecast(String cityName) {
        WeatherForecast weatherForecast;
        try {
            String url = String.format(WEATHER_URL, cityName, openWeatherMapApiKey);
            weatherForecast = restTemplate.getForObject(url, WeatherForecast.class);
        } catch (Exception e) {
            throw new CityNotFoundException("city not found");
        }
        assert weatherForecast != null;
        updateWeatherReportData(weatherForecast);

        return extractFourDaysWeatherReport(weatherForecast);
    }

    /**
     * Method to update the weather report according to our requirement.
     *
     * @param  weatherForecast weather forecast report.
     */
    private void updateWeatherReportData(WeatherForecast weatherForecast) {
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
    }

    /**
     * Method to extract only 4 days data from weather report as per the requirement.
     *
     * @param  weatherForecast weather forecast report.
     * @return WeatherForecastClient class data send to client.
     */
    private WeatherForecastClient extractFourDaysWeatherReport(WeatherForecast weatherForecast) {
        WeatherForecastClient clientDetails;
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
