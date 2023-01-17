package com.example.shop_module.app.dto;

import com.example.shop_module.app.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties
public class UserDTO {
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String matchingPassword;
    private Role role;
    private String address;
    private Long bucket_id;

    public UserDTO(String firstName, String email) {
        this.firstName = firstName;
        this.mail = email;
    }
}
