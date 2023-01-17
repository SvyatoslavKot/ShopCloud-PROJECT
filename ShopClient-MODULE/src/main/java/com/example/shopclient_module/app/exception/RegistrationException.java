package com.example.shopclient_module.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RegistrationException extends ResponseStatusException {
    public RegistrationException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
