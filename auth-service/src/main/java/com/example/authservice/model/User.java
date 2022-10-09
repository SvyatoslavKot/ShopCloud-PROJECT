package com.example.authservice.model;

import com.example.authservice.requestBeans.UserRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mail")
    private String mail;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public User(UserRequest userRequest) {
        this.mail = userRequest.getMail();
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.password = userRequest.getPassword();
        this.status = userRequest.getStatus();
        this.role = userRequest.getRole();
    }
}
