package com.example.shop_module.app.mq.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;

public abstract class KafkaAbstractProducer implements KafkaProduce {

    public KafkaTemplate kafkaTemplate;

    public KafkaAbstractProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
