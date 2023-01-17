package com.example.shop_module.app.mq;

import com.example.shop_module.app.dto.UserDTO;

import java.util.List;

public interface ProduceShopClient {

    UserDTO newClientMsg(UserDTO user);
    UserDTO getClientByMail(String mail);
    void updateClient(UserDTO userDTO);
    List<UserDTO> findAll();
}
