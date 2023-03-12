package com.example.shop_module.app.mq.kafka.producer;

public interface KafkaProduce {

    void produce(String topic, Object message);
}
