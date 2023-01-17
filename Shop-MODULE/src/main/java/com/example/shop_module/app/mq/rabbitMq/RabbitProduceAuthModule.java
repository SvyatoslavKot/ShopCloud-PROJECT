package com.example.shop_module.app.mq.rabbitMq;

import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.mq.ProduserAuthModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RabbitProduceAuthModule implements ProduserAuthModule {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public Map<Object, Object> authorization(String email, String password){
        log.info("Produce to AuthModule -> authorization, mail: {}, password: {}", email, password);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_AUTH.getValue());
        HashMap<String,String> requestMap = new HashMap<>();
        requestMap.put("password", password);
        requestMap.put("mail", email);


            Map<Object, Object> response = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_AUTH.getValue(), requestMap, ParameterizedTypeReference.forType(Map.class));
            if (response!= null) {
                log.info("Produce to AuthModule -> authorization return ->, response: {}", response);
                return response;
            }
            throw new NoConnectedToMQException();
    }
}
