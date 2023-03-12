package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.mq.rabbitMq.RabbitProducer;
import com.example.shop_module.app.mq.rabbitMq.abstraction.RabbitProducing;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public abstract class RabbitAbstractService {

    private RabbitProducing producer;

    public RabbitAbstractService(RabbitTemplate rabbitTemplate) {
        this.producer = new RabbitProducer(rabbitTemplate);
    }

    public RabbitProducing getProducer() {
        return producer;
    }

}
