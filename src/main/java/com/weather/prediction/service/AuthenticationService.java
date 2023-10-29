package com.weather.prediction.service;

import com.weather.prediction.customExceptions.InvalidAPIKeyException;
import com.weather.prediction.security.ApiKeyAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

@Service
@Slf4j
public class AuthenticationService {
    private static Environment environment;
    private static ResourceLoader resourceLoader;

    @Autowired
    public AuthenticationService(Environment environment, ResourceLoader resourceLoader) {
        AuthenticationService.environment = environment;
        AuthenticationService.resourceLoader = resourceLoader;
    }

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    public static Authentication getAuthentication(HttpServletRequest request) throws IOException {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        log.info("API KEY FOUND IN THE REQUEST HEADERS " + apiKey);
        if (apiKey == null || !apiKey.equals(getApiKey())) {
            throw new InvalidAPIKeyException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.createAuthorityList("ADMIN"));
    }

    public static String getApiKey() {
        Properties properties = new Properties();
        String apiKey = "";
        String[] activeProfile = environment.getActiveProfiles();
        Resource resource = resourceLoader.getResource("classpath:application-" + activeProfile[0] + ".properties");
        try (InputStream inputStream = resource.getInputStream()) {
            // Load the properties file
            properties.load(inputStream);
            // Get values
            apiKey = properties.getProperty("client.api.key");
        } catch (IOException e) {
            log.error("Error occurred while fetching apiKey from getApiKey() method", e);
        }
        return apiKey;
    }
}
