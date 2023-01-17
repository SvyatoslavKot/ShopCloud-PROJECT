package com.example.authservice.app.security;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAthenticationException extends AuthenticationException {

    private HttpStatus httpStatus;

    public JwtAthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public JwtAthenticationException(String msg) {
        super(msg);
    }
}
