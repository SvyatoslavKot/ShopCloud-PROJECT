package com.example.shop_module.app.service.kafkaService;

import com.example.shop_module.app.domain.MessageFromSocket;
import com.example.shop_module.app.mq.kafka.KafkaSettings;
import com.example.shop_module.app.mq.kafka.producer.KafkaProducer;
import com.example.shop_module.app.service.AlgorithmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaAlgorithmsService implements AlgorithmsService {

    @Autowired
    KafkaProducer producer;

    @Override
    public void sortBubble(MessageFromSocket message) {
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("sessionId", message.getSessionId());
        msgMap.put("message", message.getMessage());
        producer.produce(KafkaSettings.TOPIC_ALGORITHMS_SORT_BUBBLE.getValue(), msgMap);
    }

    @Override
    public void sortQuick(MessageFromSocket message) {
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("sessionId", message.getSessionId());
        msgMap.put("message", message.getMessage());
        producer.produce(KafkaSettings.TOPIC_ALGORITHMS_SORT_QUICK.getValue(), msgMap);
    }
}
