package com.example.shop_module.app.exceptions;

import org.springframework.http.HttpStatus;

public class NoConnectedToRestService extends ResponseMessageException {
    public  NoConnectedToRestService() {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Нет ответа от Rest Service");
    }
}
