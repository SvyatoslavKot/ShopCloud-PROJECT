package com.example.shop_module.app.service.abstraction;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException;

    ResponseEntity findByMail(String mail);

    ResponseEntity findAllUserDto();

    void updateProfile(UserDTO userDTO);


}
