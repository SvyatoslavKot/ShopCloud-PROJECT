package com.example.shop_module.app.service.kafkaService;

import com.example.shop_module.app.mq.kafka.producer.KafkaProduce;
import com.example.shop_module.app.mq.kafka.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaAbstractService {

    public KafkaProduce producer;

    public KafkaAbstractService(KafkaTemplate kafkaTemplate) {
        this.producer = new KafkaProducer(kafkaTemplate);

    }
}
