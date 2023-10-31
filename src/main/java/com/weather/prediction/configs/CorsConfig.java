package com.weather.prediction.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration file to filter out CORS.
 */
@Configuration
public class CorsConfig {

    /**
     * Method to create a bean for CorsFilter.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://13.127.161.181/");
        config.addAllowedOrigin("http://a593a8352e95e4961a4718af73c9bcc6-472627972.ap-south-1.elb.amazonaws.com:8081");
        config.addAllowedHeader("X-API-KEY");
        config.addAllowedMethod("GET");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
