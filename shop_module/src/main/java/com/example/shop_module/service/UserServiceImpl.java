package com.example.shop_module.service;

import com.example.shop_module.domain.Role;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.exceptions.ResponseMessageException;
import com.example.shop_module.mq.ProducerShopClient;
import com.example.shop_module.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProducerShopClient producerShopClient;


    @Override
    public List<UserDTO> findAllUserDto() {
        return  userRepository.findAll().stream().map(
                user -> new UserDTO(
                      user.getName(),
                        user.getMail()
                )).collect(Collectors.toList());
    }


    @Override
    public User finByMail(String mail) {
        return userRepository.findByMail(mail).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public UserDTO findByMail(String mail) {
// TODO: 009 09.10.22 обработать иключения
        return producerShopClient.getClientByMail(mail);
    }

    @Override
    public void updateProfile(UserDTO userDTO) {
        producerShopClient.updateClient(userDTO);
    }

    @Override
    public ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException {
        UserDTO user = producerShopClient.newClientMsg(userDTO);
        if (user != null){
            return new ResponseEntity (user, HttpStatus.OK);
        }
        return new ResponseEntity( "@mail -> " + userDTO.getMail() + " уже зарегистрирован!",HttpStatus.FORBIDDEN);

    }
}