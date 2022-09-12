package com.example.authservice.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class TokenResponse {
    private String token;
}
