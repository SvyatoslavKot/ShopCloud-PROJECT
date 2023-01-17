package com.example.productmodule.app.mq.rabbitMq;

import com.example.productmodule.app.mq.ProduceClientShop;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitProduceClientShop implements ProduceClientShop {

    private final RabbitProducer producer;

    @Override
    public void addBucketClient(String mail, Long bucketId){
        Map<String , Object> sendMsg = new HashMap<>();
        sendMsg.put("bucketId", bucketId);
        sendMsg.put("mail", mail);
        producer.produce(RabbitMqSettings.EXCHANGE_CLIENT_MODULE_ADD_BUCKET_CLIENT.getValue(),
                        RabbitMqSettings.ROUTIN_KEY_CLIENT_MODULE_ADD_BUCKET_CLIENT.getValue(),
                        sendMsg);
    }
}
