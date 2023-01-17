package com.example.shopclient_module.app.domain;


import com.example.shopclient_module.app.dto.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor

public class ShopClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String mail;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "address")
    private String address;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "bucket_id")
    private Long bucketId;

    public ShopClient (UserDto dto){
        this.name = dto.getFirstName();
        this.mail = dto.getMail();
        this.lastname = dto.getLastName();
        this.status = dto.getStatus();
        this.role = dto.getRole();
        this.status = dto.getStatus();
        this.address = dto.getAddress();
        this.bucketId = dto.getBucketId();
    }
}
