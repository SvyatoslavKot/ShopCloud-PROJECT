package com.example.shop_module.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginClientException extends ResponseStatusException {
    public LoginClientException(HttpStatus status, String reason) {
        super(status, reason);
    }
}