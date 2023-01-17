package com.example.shopclient_module.app.mq.rabbitMq.producers;


import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.mq.ProduceAuth;
import com.example.shopclient_module.app.mq.rabbitMq.RabbitMqSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitProduceAuth implements ProduceAuth {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitProducer producer;

    public void newClientEvent(UserDto user){
       // rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_AUTH_MODULE_NEW_CLIENT.getValue());
        log.info("Producer Product id -> " + user);
       // rabbitTemplate.convertAndSend(RabbitMqSetting.ROUTING_KEY_AUTH_MODULE_NEW_CLIENT.getValue(), user);
        producer.produce(RabbitMqSetting.EXCHANGE_AUTH_MODULE_NEW_CLIENT.getValue(),
                        RabbitMqSetting.ROUTING_KEY_AUTH_MODULE_NEW_CLIENT.getValue(),
                        user);
    }
}

