package com.example.shop_module.app.exceptions;

import org.springframework.http.HttpStatus;

public class NoConnectedToGRpsServer extends ResponseMessageException {
    public  NoConnectedToGRpsServer() {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Нет ответа.");
    }
}