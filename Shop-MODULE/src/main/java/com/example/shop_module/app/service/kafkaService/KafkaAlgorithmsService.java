package com.example.shop_module.app.service.kafkaService;

import com.example.shop_module.app.domain.MessageFromSocket;
import com.example.shop_module.app.mq.kafka.KafkaSettings;
import com.example.shop_module.app.service.abstraction.AlgorithmsService;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;


public class KafkaAlgorithmsService  extends KafkaAbstractService implements AlgorithmsService {

    public KafkaAlgorithmsService(KafkaTemplate kafkaTemplate) {
        super(kafkaTemplate);
    }

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
