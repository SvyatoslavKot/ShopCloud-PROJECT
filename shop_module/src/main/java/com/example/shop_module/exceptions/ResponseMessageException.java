package com.example.shop_module.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseMessageException extends ResponseStatusException {
    public ResponseMessageException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
