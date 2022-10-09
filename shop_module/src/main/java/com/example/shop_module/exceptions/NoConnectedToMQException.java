package com.example.shop_module.exceptions;

import org.springframework.http.HttpStatus;

public class NoConnectedToMQException extends ResponseMessageException{
    public NoConnectedToMQException() {
        super(HttpStatus.NO_CONTENT, "Нет ответа.");
    }
}
