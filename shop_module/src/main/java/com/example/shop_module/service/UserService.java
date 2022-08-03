package com.example.shop_module.service;

import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;

import java.util.List;

public interface UserService {

    boolean save(UserDTO userDTO);

    void save(User user);

    List<UserDTO> findAllUserDto();

    User finByMail(String name);

    void updateProfile(UserDTO userDTO);

}
