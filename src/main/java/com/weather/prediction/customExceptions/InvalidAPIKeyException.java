package com.weather.prediction.customExceptions;

public class InvalidAPIKeyException extends RuntimeException{
    public InvalidAPIKeyException(String message){
        super(message);
    }
}
