package com.weather.prediction;

import com.weather.prediction.serverDTO.WeatherForecast;
import com.weather.prediction.service.WeatherForecastingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * Class to test weather forecast service logic.
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class WeatherForecastServiceIntegrationTest extends AbstractTest {

    @Autowired
    WeatherForecastingService weatherForecastingService;

    @Before
    public void before() {
        setUp();
    }

    @Test
    public void testWeatherReportLogic_RainingAndHighWind() throws IOException {
        String weatherReportJson = "{\"cod\":\"200\",\"message\":\"0\",\"list\":[{\"main\":{\"temp\":30.05," +
                "\"feels_like\":29.22,\"temp_min\":30.05,\"temp_max\":30.8},\"weather\":[{\"description\"" +
                ":\"clear sky\",\"raining\":false,\"highWinds\":false,\"thunderstorm\":false,\"rainPredicted\"" +
                ":false,\"tooHot\":false,\"main\":\"rain\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":11}," +
                "\"date\":null,\"dt\":\"1698840000\"}],\"city\":{\"name\":\"Delhi\",\"country\":\"IN\"}}";
        WeatherForecast weatherForecast = mapFromJson(weatherReportJson, WeatherForecast.class);
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isRaining());
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isHighWinds());
        weatherForecastingService.updateWeatherReportData(weatherForecast);
        Assertions.assertTrue(weatherForecast.getList().get(0).getWeather().get(0).isRaining());
        Assertions.assertTrue(weatherForecast.getList().get(0).getWeather().get(0).isHighWinds());
    }

    @Test
    public void testWeatherReportLogic_TooHot() throws IOException {
        String weatherReportJson = "{\"cod\":\"200\",\"message\":\"0\",\"list\":[{\"main\":{\"temp\":41.05," +
                "\"feels_like\":29.22,\"temp_min\":30.05,\"temp_max\":30.8},\"weather\":[{\"description\"" +
                ":\"clear sky\",\"raining\":false,\"highWinds\":false,\"thunderstorm\":false,\"rainPredicted\"" +
                ":false,\"tooHot\":false,\"main\":\"clear sky\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1}," +
                "\"date\":null,\"dt\":\"1698840000\"}],\"city\":{\"name\":\"Delhi\",\"country\":\"IN\"}}";
        WeatherForecast weatherForecast = mapFromJson(weatherReportJson, WeatherForecast.class);
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isTooHot());
        weatherForecastingService.updateWeatherReportData(weatherForecast);
        Assertions.assertTrue(weatherForecast.getList().get(0).getWeather().get(0).isTooHot());
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isRainPredicted());
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isThunderstorm());
    }

    @Test
    public void testWeatherReportLogic_Thunderstorm() throws IOException {
        String weatherReportJson = "{\"cod\":\"200\",\"message\":\"0\",\"list\":[{\"main\":{\"temp\":20.05," +
                "\"feels_like\":29.22,\"temp_min\":30.05,\"temp_max\":30.8},\"weather\":[{\"description\"" +
                ":\"clear sky\",\"raining\":false,\"highWinds\":false,\"thunderstorm\":false,\"rainPredicted\"" +
                ":false,\"tooHot\":false,\"main\":\"thunderstorm\"}],\"clouds\":{\"all\":70},\"wind\":{\"speed\":1}," +
                "\"date\":null,\"dt\":\"1698840000\"}],\"city\":{\"name\":\"Delhi\",\"country\":\"IN\"}}";
        WeatherForecast weatherForecast = mapFromJson(weatherReportJson, WeatherForecast.class);
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isThunderstorm());
        weatherForecastingService.updateWeatherReportData(weatherForecast);
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isTooHot());
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isRaining());
        Assertions.assertTrue(weatherForecast.getList().get(0).getWeather().get(0).isThunderstorm());
    }

    @Test
    public void testWeatherReportLogic_RainPredicted() throws IOException {
        String weatherReportJson = "{\"cod\":\"200\",\"message\":\"0\",\"list\":[{\"main\":{\"temp\":20.05," +
                "\"feels_like\":29.22,\"temp_min\":30.05,\"temp_max\":30.8},\"weather\":[{\"description\"" +
                ":\"clear sky\",\"raining\":false,\"highWinds\":false,\"thunderstorm\":false,\"rainPredicted\"" +
                ":false,\"tooHot\":false,\"main\":\"clouds\"}],\"clouds\":{\"all\":70},\"wind\":{\"speed\":1}," +
                "\"date\":null,\"dt\":\"1698840000\"}],\"city\":{\"name\":\"Delhi\",\"country\":\"IN\"}}";
        WeatherForecast weatherForecast = mapFromJson(weatherReportJson, WeatherForecast.class);
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isThunderstorm());
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isTooHot());
        weatherForecastingService.updateWeatherReportData(weatherForecast);
        Assertions.assertFalse(weatherForecast.getList().get(0).getWeather().get(0).isTooHot());
        Assertions.assertTrue(weatherForecast.getList().get(0).getWeather().get(0).isRainPredicted());
    }
}
