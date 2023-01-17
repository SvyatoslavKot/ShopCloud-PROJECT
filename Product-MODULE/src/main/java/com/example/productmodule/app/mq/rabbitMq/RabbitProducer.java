package com.example.productmodule.app.mq.rabbitMq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
@AllArgsConstructor
public class RabbitProducer {
    private final RabbitTemplate rabbitTemplate;

    public void produce (String exchange, String routingKey, Object value) {
        System.out.println("Produce Method !!");
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.convertAndSend(routingKey,value);
    }
}
