package com.example.shopclient_module.dto;

import com.example.shopclient_module.domain.Role;
import com.example.shopclient_module.domain.ShopClient;
import com.example.shopclient_module.domain.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Builder
public class UserDto {

    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String matchingPassword;
    private Status status;
    private Role role;
    private String address;
    private Long bucketId;


    public UserDto (ShopClient client) {
        this.firstName = client.getName();
        this.lastName = client.getLastname();
        this.mail = client.getMail();
        this.status = client.getStatus();
        this.role = client.getRole();
        this.address = client.getAddress();
        this.bucketId = client.getBucketId();
    }

    public UserDto(String username, String mail) {
        this.firstName = username;
        this.mail = mail;
    }
}
