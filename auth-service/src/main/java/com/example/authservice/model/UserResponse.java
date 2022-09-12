package com.example.authservice.model;

import lombok.Data;
import lombok.Getter;


@Data
@Getter
public class UserResponse {
    private String mail;
    private String firstName;
    private String lastName;
    private String status;

    public UserResponse(User user) {
        this.mail = user.getMail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.status = user.getStatus();

    }
}
