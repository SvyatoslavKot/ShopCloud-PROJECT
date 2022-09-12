package com.example.eurikaclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class UserRequest {
    private String mail;
    private String firstName;
    private String lastName;
    private String password;
    private String status;
}