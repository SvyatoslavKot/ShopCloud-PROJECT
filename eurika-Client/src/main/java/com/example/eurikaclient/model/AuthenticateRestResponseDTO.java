package com.example.eurikaclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
public class AuthenticateRestResponseDTO {
    private String email;
    private String token;
}
