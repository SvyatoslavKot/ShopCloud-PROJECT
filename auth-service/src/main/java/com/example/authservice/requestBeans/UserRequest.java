package com.example.authservice.requestBeans;

import com.example.authservice.model.Role;
import com.example.authservice.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties
public class UserRequest {
    private String mail;
    private String firstName;
    private String lastName;
    private String password;
    private Status status;
    private Role role;
}
