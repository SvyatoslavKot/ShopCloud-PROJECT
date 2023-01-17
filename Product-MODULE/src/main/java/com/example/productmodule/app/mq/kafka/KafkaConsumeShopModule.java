package com.example.productmodule.app.mq.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumeShopModule {

    private final ObjectMapper objectMapper;

/*
    @KafkaListener(groupId = GROUP_ID, topics = TOPIC_RATE_RESPONSE)
    public void consume(String responseMsg) {
        try {
            SendMessage sendMessage = objectMapper.readValue(responseMsg, SendMessage.class);
            updateController.setAnswerMessage(sendMessage);
        } catch (JsonProcessingException e) {
            System.out.println("Message can't parse! Message -> " + responseMsg);
        }

    }

    */

}
