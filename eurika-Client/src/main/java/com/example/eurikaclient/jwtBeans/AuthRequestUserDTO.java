package com.example.eurikaclient.jwtBeans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequestUserDTO {
    private String mail;
    private String password;
}
