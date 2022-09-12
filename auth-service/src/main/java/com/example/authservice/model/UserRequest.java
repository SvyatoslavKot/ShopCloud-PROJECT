package com.example.authservice.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserRequest {
    private String mail;
    private String firstName;
    private String lastName;
    private String password;
    private String status;
}
