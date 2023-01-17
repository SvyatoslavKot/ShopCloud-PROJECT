package com.example.shopclient_module.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class UserAuthDto {
    private String mail;
    private String firstName;
    private String lastName;
    private String password;
    private String status;
}
