package com.weather.prediction;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@OpenAPIDefinition(
        info = @Info(
                title = "Weather prediction microservice",
                version = "1.0",
                description = "Fetch weather report based on the city names"
        )
)
public class WeatherPredictionServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherPredictionServerApplication.class, args);
//        SpringDocUtils.init();
    }

}
