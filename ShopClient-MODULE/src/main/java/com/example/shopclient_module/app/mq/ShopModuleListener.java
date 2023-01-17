package com.example.shopclient_module.app.mq;

import com.example.shopclient_module.app.exception.RegistrationException;
import com.example.shopclient_module.app.dto.UserDto;

import java.util.List;

public interface ShopModuleListener {

    UserDto newClient( UserDto userDto) throws RegistrationException;
    UserDto getClient( String mail) throws InterruptedException;
    List<UserDto> findAllClient();
    void updateClient( UserDto request);
}
