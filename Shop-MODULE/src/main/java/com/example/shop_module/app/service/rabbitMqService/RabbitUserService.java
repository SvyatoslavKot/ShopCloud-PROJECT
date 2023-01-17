package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceShopClient;
import com.example.shop_module.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RabbitUserService implements UserService {

    private final ProduceShopClient producerShopClient;

    public RabbitUserService(@Qualifier("rabbitProduceShopClient") ProduceShopClient producerShopClient) {
        this.producerShopClient = producerShopClient;
    }

    @Override
    public ResponseEntity findByMail(String mail) {
       try{
           UserDTO userDTO = producerShopClient.getClientByMail(mail);
           return new ResponseEntity(userDTO, HttpStatus.OK);
       }catch (NoConnectedToMQException e){
           return new ResponseEntity(e.getMessage(),e.getStatus());
       }catch (ResponseMessageException rme) {
           return new ResponseEntity(rme.getMessage(), rme.getStatus());
       }
    }

    @Override
    public ResponseEntity findAllUserDto() {
        try {
            List<UserDTO> userList = producerShopClient.findAll();
            return new ResponseEntity(userList, HttpStatus.OK);
        }catch (NoConnectedToMQException nce) {
            return new ResponseEntity(nce.getMessage(), nce.getStatus());
        }catch (ResponseMessageException rme) {
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }
    }


    @Override
    public void updateProfile(UserDTO userDTO) {
        producerShopClient.updateClient(userDTO);
    }

    @Override
    public ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException {
        try{
            UserDTO user = producerShopClient.newClientMsg(userDTO);
            return new ResponseEntity (user, HttpStatus.OK);
        }catch (NoConnectedToMQException nce) {
            return new ResponseEntity(nce.getMessage(), nce.getStatus());
        }catch (ResponseMessageException rme) {
            return new ResponseEntity( rme.getMessage(),rme.getStatus());
        }
    }
}
