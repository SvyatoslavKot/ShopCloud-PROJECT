package com.example.productmodule.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class ShopClient {

    private Long id;
    private String mail;
    private String name;
    private String lastname;
    //private Status status;
    private String address;
   // private Role role;
    private Long bucketId;
/*
    public ShopClient (UserDto dto){
        this.name = dto.getFirstName();
        this.mail = dto.getMail();
        this.lastname = dto.getLastName();
        this.status = dto.getStatus();
        this.role = dto.getRole();
        this.status = dto.getStatus();
        this.address = dto.getAddress();
    }

 */
}
