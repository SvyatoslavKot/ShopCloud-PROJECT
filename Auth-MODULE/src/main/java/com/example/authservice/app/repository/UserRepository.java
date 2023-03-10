package com.example.authservice.app.repository;

import com.example.authservice.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMail(String mail);
}
