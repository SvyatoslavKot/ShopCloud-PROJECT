package com.example.shop_module.app.mq.rabbitMq;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceShopClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RabbitProduceShopClient implements ProduceShopClient {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public UserDTO newClientMsg(UserDTO user){
        log.info("Produce to ClientModule -> new Client, UserDTO: {}", user);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_SHOPCLIENT_NEW.getValue());
        UserDTO response =  rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_SHOPCLIENT_NEW.getValue(), user, ParameterizedTypeReference.forType(UserDTO.class));
        if (response != null){
            if (response.getMail() != null) {
                log.info("Produce to ClientModule return -> {} ", response);
                return response;
            }
            throw new ResponseMessageException(HttpStatus.CONFLICT, "Uncorrect user data");
        }
        throw new NoConnectedToMQException();

    }

    @Override
    public UserDTO getClientByMail(String mail){
        log.info("Produce to ClientModule -> get Client by mail, mail: {}", mail);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_GET_CLIENT.getValue());
        UserDTO response = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_GET_CLIENT.getValue(), mail, ParameterizedTypeReference.forType(UserDTO.class));
        if (response != null) {
            if (response.getMail() != null){
                log.info("Produce to ClientModule return -> {} ", response);
                return response;
            }
            throw  new ResponseMessageException(HttpStatus.NOT_FOUND, "Client with mail <" + mail + "> not found!");
        }
        throw new NoConnectedToMQException();
    }

    @Override
    public void updateClient(UserDTO userDTO){
        log.info("Produce to ClientModule -> update Client, userDTO: {}", userDTO);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_UPDATE_CLIENT.getValue());
        rabbitTemplate.convertAndSend( userDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        log.info("Produce to ClientModule -> find all Clients");
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_FIND_ALL_CLIENT.getValue());
        List<UserDTO> userList = rabbitTemplate.convertSendAndReceiveAsType(
                RabbitMqSetting.ROUTING_KEY_FIND_ALL_CLIENT.getValue(),
                ParameterizedTypeReference.forType(List.class));
        if ( userList != null ){
            if ( !userList.isEmpty() ) {
                return userList;
            }
            throw new ResponseMessageException(HttpStatus.NO_CONTENT, "Clients not found");
        }
        throw new NoConnectedToMQException();
     }
}
