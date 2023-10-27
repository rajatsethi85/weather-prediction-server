package com.weather.prediction.customExceptions;

public class CityNotFoundException
        extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
}
