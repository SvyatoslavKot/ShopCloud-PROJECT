package com.example.authservice.service;

import com.example.authservice.model.User;
import com.example.authservice.requestBeans.UserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    public void saveUser(User user);
    User findByMail(String mail) throws UsernameNotFoundException;

    void updateUser(UserRequest dto);
}
