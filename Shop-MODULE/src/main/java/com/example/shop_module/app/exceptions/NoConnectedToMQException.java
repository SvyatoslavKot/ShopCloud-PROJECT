package com.example.shop_module.app.exceptions;

import org.springframework.http.HttpStatus;

public class NoConnectedToMQException extends ResponseMessageException{
    public NoConnectedToMQException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Нет ответа попробуте позже.");
    }
}
