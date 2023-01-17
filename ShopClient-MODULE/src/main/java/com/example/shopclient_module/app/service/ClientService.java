package com.example.shopclient_module.app.service;

import com.example.shopclient_module.app.dto.UserDto;

import java.util.List;

public interface ClientService {

    UserDto registrationClient(UserDto requestClient);
    void updateClient(UserDto userDto);
    void addBucket(String mail, Long bucketId);
    List<UserDto> findAllClient();
    UserDto findByMail(String mail);
}
