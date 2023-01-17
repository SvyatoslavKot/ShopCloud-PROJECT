package com.example.shopclient_module.app.mq.rabbitMq.producers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void produce(String exchange, String routingKey, Object msgValue){
        rabbitTemplate.setExchange(exchange);
        log.info("Producer  -> " + msgValue);
        rabbitTemplate.convertAndSend(routingKey, msgValue);
    }
}
