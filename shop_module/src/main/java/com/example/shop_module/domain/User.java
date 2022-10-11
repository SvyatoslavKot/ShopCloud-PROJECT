package com.example.shop_module.domain;

import com.example.shop_module.domain.enums.Role;
import com.example.shop_module.domain.enums.Status;
import com.example.shop_module.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Table(name = "users")
@JsonIgnoreProperties
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( name = "name")
    private String name;
    @Transient
    private String lastname;
    @Column(name = "password")
    private String password;
    @Column(name = "e_mail")
    private String mail;
    @Transient
    private Status status;
    @Enumerated(EnumType.STRING)
    private Role role;
    //@OneToOne(cascade = CascadeType.REMOVE)
    //@JoinColumn(name = "bucket_id")
    //private Bucket bucket;
    private String address;

/*
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name ="products_users",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))

 @Transient
    private List<Product> favoriteProductList;
*/
    public User(UserDTO dto) {
        this.mail = dto.getMail();
        this.name = dto.getFirstName();
        this.lastname = dto.getLastName();
        this.password = dto.getPassword();
        this.role = dto.getRole();
        this.address = dto.getAddress();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", role=" + role +
                '}';
    }
}
