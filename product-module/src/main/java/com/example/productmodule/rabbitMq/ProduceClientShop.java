package com.example.productmodule.rabbitMq;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class ProduceClientShop {

    private final RabbitSettings settings;
    private final RabbitTemplate rabbitTemplate;

    @Bean
    public Queue queueAddBucketClient() {
        return new Queue(settings.getQueueAddBucketClient(), false);
    }
    @Bean
    public TopicExchange topicExchangeAddBucketClient(){
        return new TopicExchange(settings.getExchangeAddBucketClient());
    }

    @Bean
    public Binding bindingAddBucketClient(Queue queueAddBucketClient, TopicExchange topicExchangeAddBucketClient){
        return BindingBuilder.bind(queueAddBucketClient).to(topicExchangeAddBucketClient).with(settings.getRoutingKeyAddBucketClient());
    }

    public void addBucketClient(String mail, Long bucketId){
        rabbitTemplate.setExchange(settings.getExchangeAddBucketClient());
        Map<String , Object> sendMsg = new HashMap<>();
        sendMsg.put("bucketId", bucketId);
        sendMsg.put("mail", mail);
        rabbitTemplate.convertAndSend(settings.getRoutingKeyAddBucketClient(),sendMsg);
    }
}
