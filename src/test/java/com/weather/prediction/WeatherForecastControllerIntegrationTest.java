package com.weather.prediction;

import com.weather.prediction.clientDTO.WeatherForecastClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class to write test cases for the weather forecast API.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class WeatherForecastControllerIntegrationTest extends AbstractTest{

    @Value("client.api.key")
    private String API_KEY;
    @Before
    public void before() {
        setUp();
    }

    @Test
    public void testGetWeatherReport_StatusOk() throws Exception {
        String cityName = "Delhi";
        String uri = "/forecast";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .param("cityName", cityName).header("X-API-KEY", API_KEY)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        WeatherForecastClient weatherForecastClient = super.mapFromJson(content, WeatherForecastClient.class);
        Assert.assertEquals(cityName, weatherForecastClient.getCity().getName());
    }

    @Test
    public void testGetWeatherReport_WrongCityName() {
        String cityName = "Invalid";
        String uri = "/forecast";
        Assertions.assertThrows(Exception.class, () -> mvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("cityName", cityName).header("X-API-KEY", API_KEY)), "city not found");
    }
}
