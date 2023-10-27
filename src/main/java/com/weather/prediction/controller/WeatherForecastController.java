package com.weather.prediction.controller;

import com.weather.prediction.clientDTO.WeatherForecastClient;
import com.weather.prediction.service.WeatherForcastingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forecast")
@SecurityScheme(
        name = "X-API-KEY",
        type = SecuritySchemeType.APIKEY,
        scheme = "APIKEY"
)
@Tag(name = "WeatherForecastController", description = "Controller for managing weather forecast API endpoint.")
public class WeatherForecastController {
    @Autowired
    private WeatherForcastingService weatherForcastingService;

    @GetMapping()
    @Operation(summary = "Get Weather report by city name", description = "Get an item by its name.")
    @SecurityRequirement(name = "API key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Successful response",
                         content = @Content(schema = @Schema(implementation = WeatherForecastClient.class))),
            @ApiResponse(responseCode = "404",
                         description = "city not found")
    })
    @Parameter(name = "X-API-KEY",in = ParameterIn.HEADER)
    public WeatherForecastClient getWeatherForecast(@Parameter(description = "Name of city", required = true)
                                                    @RequestParam(value = "cityName") final String cityName) {
        return weatherForcastingService.forcast(cityName);
    }
}
