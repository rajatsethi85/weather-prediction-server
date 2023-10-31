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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Service class to handle authentication for the request.
 */
@Service
@Slf4j
public class AuthenticationService {
    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static Environment environment;
    private static ResourceLoader resourceLoader;

    @Autowired
    public AuthenticationService(Environment environment, ResourceLoader resourceLoader) {
        AuthenticationService.environment = environment;
        AuthenticationService.resourceLoader = resourceLoader;
    }

    /**
     * Get Authentication for the request.
     *
     * @return Authentication
     */
    public static Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(getSecretApiKey())) {
            throw new InvalidAPIKeyException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.createAuthorityList("ADMIN"));
    }

    /**
     * Get Secret API key from the properties file.
     * @return String authenticationApiKey.
     */
    public static String getSecretApiKey() {
        Properties properties = new Properties();
        String authenticationApiKey = "";
        String[] activeProfile = environment.getActiveProfiles();
        Resource resource = resourceLoader.getResource("classpath:application-" + activeProfile[0] + ".properties");
        try (InputStream inputStream = resource.getInputStream()) {
            // Load the properties file
            properties.load(inputStream);
            // Get values
            authenticationApiKey = properties.getProperty("client.api.key");
        } catch (IOException e) {
            log.error("Error occurred while fetching apiKey from getApiKey() method", e);
        }
        return authenticationApiKey;
    }
}
