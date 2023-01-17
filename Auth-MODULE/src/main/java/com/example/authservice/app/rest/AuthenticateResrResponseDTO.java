package com.example.authservice.app.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class AuthenticateResrResponseDTO {
    private String email;
    private String token;
}
