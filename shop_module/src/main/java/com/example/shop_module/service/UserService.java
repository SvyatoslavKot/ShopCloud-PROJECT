package com.example.shop_module.service;

import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.exceptions.ResponseMessageException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserDTO findByMail(String mail);

    List<UserDTO> findAllUserDto();
    User finByMail(String name);
    void updateProfile(UserDTO userDTO);
    ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException;

}
