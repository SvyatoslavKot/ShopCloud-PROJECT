package com.example.eurikaclient.provider;

import lombok.Getter;
import lombok.Getter;
import org.springframework.http.HttpStatus;


import javax.naming.AuthenticationException;

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
