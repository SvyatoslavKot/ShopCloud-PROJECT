package com.example.productmodule.rabbitMq;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RabbitSettings {

    @Value("${rabbitmq.exchange.addBucketClient}")
    private String exchangeAddBucketClient;

    @Value("${rabbitmq.routingkey.addBucketClient}")
    private String routingKeyAddBucketClient;

    @Value("${rabbitmq.queue.addBucketClient}")
    private String queueAddBucketClient;
}
