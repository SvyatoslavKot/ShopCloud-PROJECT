package com.example.shop_module.app.mq.rabbitMq.abstraction;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public abstract class RabbitAbstractProducer implements RabbitProducing{

    public RabbitTemplate rabbitTemplate;

    public RabbitAbstractProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
