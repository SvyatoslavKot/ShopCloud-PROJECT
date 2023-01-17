package com.example.authservice.app.requestBeans;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class TokenResponse {
    private String token;
}
