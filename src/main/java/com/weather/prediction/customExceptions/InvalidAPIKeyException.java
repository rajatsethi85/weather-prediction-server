package com.weather.prediction.customExceptions;

/**
 * Custom exception class.
 */
public class InvalidAPIKeyException extends RuntimeException{
    public InvalidAPIKeyException(String message){
        super(message);
    }
}
