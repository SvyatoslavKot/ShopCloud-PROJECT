package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.exceptions.LoginClientException;
import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import com.example.shop_module.app.mq.rabbitMq.abstraction.RabbitProducing;
import com.example.shop_module.app.service.abstraction.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RabbitAuthService extends RabbitAbstractService implements AuthService {

    private RabbitProducing producer;

    public RabbitAuthService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
        this.producer = getProducer();
    }

    @Override
    public ResponseEntity authorization(String email, String password) {
        log.info("Produce to AuthModule -> authorization, mail: {}, password: {}", email, password);

        HashMap<String,String> requestMap = new HashMap<>();
        requestMap.put("password", password);
        requestMap.put("mail", email);

        Map<Object, Object> response = producer.convertSendAndReceiveMap(RabbitMqSetting.EXCHANGE_AUTH.getValue(),
                RabbitMqSetting.ROUTING_KEY_AUTH.getValue(),
                requestMap);

        if (response!= null) {
            log.info("Produce to AuthModule -> authorization return ->, response: {}", response);
            if(response.get(HttpStatus.OK.name()) != null){
                return new ResponseEntity(response.get(HttpStatus.OK.name()), HttpStatus.OK);
            }
        }
        //throw new NoConnectedToMQException();
        throw new LoginClientException(
                HttpStatus.NON_AUTHORITATIVE_INFORMATION,
                (String)response.get(HttpStatus.NON_AUTHORITATIVE_INFORMATION.name()));
    }
}
