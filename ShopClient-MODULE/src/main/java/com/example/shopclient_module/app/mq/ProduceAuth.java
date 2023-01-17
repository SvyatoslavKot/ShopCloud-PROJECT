package com.example.shopclient_module.app.mq;

import com.example.shopclient_module.app.dto.UserDto;


public interface ProduceAuth {

    void newClientEvent(UserDto user);

}
