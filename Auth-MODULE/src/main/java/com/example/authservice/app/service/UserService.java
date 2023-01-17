package com.example.authservice.app.service;

import com.example.authservice.app.model.User;
import com.example.authservice.app.requestBeans.UserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    public void saveUser(User user);
    User findByMail(String mail) throws UsernameNotFoundException;

    void updateUser(UserRequest dto);
}
