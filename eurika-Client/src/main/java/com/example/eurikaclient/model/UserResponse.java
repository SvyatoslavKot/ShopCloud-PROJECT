package com.example.eurikaclient.model;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserResponse {
    private String mail;
    private String firstName;
    private String lastName;
    private String status;
}