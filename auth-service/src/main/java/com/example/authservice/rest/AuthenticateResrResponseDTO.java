package com.example.authservice.rest;

import lombok.Data;

@Data
public class AuthenticateResrResponseDTO {
    private String email;
    private String token;
}
