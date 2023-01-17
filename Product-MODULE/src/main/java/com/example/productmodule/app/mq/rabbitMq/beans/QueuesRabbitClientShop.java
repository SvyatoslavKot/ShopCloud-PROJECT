package com.example.productmodule.app.mq.rabbitMq.beans;

import com.example.productmodule.app.mq.rabbitMq.RabbitMqSettings;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueuesRabbitClientShop {

    @Bean
    public Queue queueAddBucketClient() {
        return new Queue(RabbitMqSettings.QUEUE_CLIENT_MODULE_ADD_BUCKET_CLIENT.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeAddBucketClient(){
        return new TopicExchange(RabbitMqSettings.EXCHANGE_CLIENT_MODULE_ADD_BUCKET_CLIENT.getValue());
    }
    @Bean
    public Binding bindingAddBucketClient(Queue queueAddBucketClient, TopicExchange topicExchangeAddBucketClient){
        return BindingBuilder.bind(queueAddBucketClient).to(topicExchangeAddBucketClient).with(RabbitMqSettings.ROUTIN_KEY_CLIENT_MODULE_ADD_BUCKET_CLIENT.getValue());
    }

    @Bean
    public Queue queueConfirmOrderFromBucket() {
        return new Queue(RabbitMqSettings.QUEUE_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeConfirmOrderFromBucket(){
        return new TopicExchange(RabbitMqSettings.EXCHANGE_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET.getValue());
    }
    @Bean
    public Binding bindingConfirmOrderFromBucket(Queue queueConfirmOrderFromBucket, TopicExchange topicExchangeConfirmOrderFromBucket){
        return BindingBuilder.bind(queueConfirmOrderFromBucket).to(topicExchangeConfirmOrderFromBucket).with(RabbitMqSettings.ROUTIN_KEY_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET.getValue());
    }




}
