package com.weather.prediction.customExceptions;


/**
 * Custom exception class.
 */
public class CityNotFoundException
        extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
}
