package com.example.shopclient_module.app.dto;

import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.domain.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Builder
public class UserDto implements Cloneable{

    @ApiModelProperty(notes = "ClientShop name")
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String matchingPassword;
    private Status status;
    private Role role;
    private String address;
    @JsonProperty("bucket_id")
    private Long   bucketId;


    public UserDto (ShopClient client) {
        this.firstName = client.getName();
        this.lastName  = client.getLastname();
        this.mail      = client.getMail();
        this.status    = client.getStatus();
        this.role      = client.getRole();
        this.address   = client.getAddress();
        this.bucketId  = client.getBucketId();
    }

    public UserDto(String username, String mail) {
        this.firstName = username;
        this.mail      = mail;
    }

    @Override
    public UserDto clone() throws CloneNotSupportedException {
        return new UserDto(this.firstName,
                this.lastName,
                this.mail,
                this.password,
                this.matchingPassword,
                this.status,
                this.role,
                this.address,
                this.bucketId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(mail, userDto.mail) && Objects.equals(password, userDto.password) && Objects.equals(matchingPassword, userDto.matchingPassword) && status == userDto.status && role == userDto.role && Objects.equals(address, userDto.address) && Objects.equals(bucketId, userDto.bucketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, mail, password, matchingPassword, status, role, address, bucketId);
    }
}
