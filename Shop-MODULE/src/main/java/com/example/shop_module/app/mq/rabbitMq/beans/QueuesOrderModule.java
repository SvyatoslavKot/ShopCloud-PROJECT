package com.example.shop_module.app.mq.rabbitMq.beans;

import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueuesOrderModule {

    @Bean
    public Queue queueOrderCreate() {
        return new Queue(RabbitMqSetting.QUEUE_ORDER_CREATE.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeOrderCreate(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_ORDER_CREATE.getValue());
    }
    @Bean
    public Binding bindingOrderCreate(Queue queueOrderCreate, TopicExchange topicExchangeOrderCreate){
        return BindingBuilder.bind(queueOrderCreate).to(topicExchangeOrderCreate).with(RabbitMqSetting.ROUTING_KEY_ORDER_CREATE.getValue());
    }



    @Bean
    public Queue queueOrderGet() {
        return new Queue(RabbitMqSetting.QUEUE_ORDER_GET.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeOrderGet(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_ORDER_GET.getValue());
    }
    @Bean
    public Binding bindingOrderGet(Queue queueOrderGet, TopicExchange topicExchangeOrderGet){
        return BindingBuilder.bind(queueOrderGet).to(topicExchangeOrderGet).with(RabbitMqSetting.ROUTING_KEY_ORDER_GET.getValue());
    }




    @Bean
    public Queue queueOrderUpdate() {
        return new Queue(RabbitMqSetting.QUEUE_ORDER_UPDATE.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeOrderUpdate(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_ORDER_UPDATE.getValue());
    }
    @Bean
    public Binding bindingOrderUpdate(Queue queueOrderUpdate, TopicExchange topicExchangeOrderUpdate){
        return BindingBuilder.bind(queueOrderUpdate).to(topicExchangeOrderUpdate).with(RabbitMqSetting.ROUTING_KEY_ORDER_UPDATE.getValue());

    }

}
