package com.example.shop_module.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String matchingPassword;

    private String email;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
