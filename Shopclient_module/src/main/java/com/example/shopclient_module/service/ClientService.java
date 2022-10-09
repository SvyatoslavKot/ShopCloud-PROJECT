package com.example.shopclient_module.service;

import com.example.shopclient_module.dto.UserDto;

public interface ClientService {

    UserDto registrationClient(UserDto requestClient);
    void updateClient(UserDto userDto);
    void addBucket(String mail, Long bucketId);
}
