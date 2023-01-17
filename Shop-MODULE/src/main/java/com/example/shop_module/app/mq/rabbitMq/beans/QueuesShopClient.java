package com.example.shop_module.app.mq.rabbitMq.beans;

import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueuesShopClient {


    @Bean
    public Queue queueShopclien() {
        return new Queue(RabbitMqSetting.QUEUE_SHOPCLIENT_NEW.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeShopclien(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_SHOPCLIENT_NEW.getValue());
    }
    @Bean
    public Binding bindingShopclien(Queue queueShopclien, TopicExchange topicExchangeShopclien) {
        return BindingBuilder.bind(queueShopclien).to(topicExchangeShopclien).with(RabbitMqSetting.ROUTING_KEY_SHOPCLIENT_NEW.getValue());
    }



    @Bean
    public Queue queueShopcliengetClient() {
        return new Queue(RabbitMqSetting.QUEUE_GET_CLIENT.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeShopclienGetClient(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_GET_CLIENT.getValue());
    }
    @Bean
    public Binding bindingShopclienGetClient(Queue queueShopcliengetClient, TopicExchange topicExchangeShopclienGetClient){
        return BindingBuilder.bind(queueShopcliengetClient).to(topicExchangeShopclienGetClient).with(RabbitMqSetting.ROUTING_KEY_GET_CLIENT.getValue());
    }



    @Bean
    public Queue queueUpdateClientToClient() {
        return new Queue(RabbitMqSetting.QUEUE_UPDATE_CLIENT_TO_CLIENTMODULE.getValue());
    }
    @Bean
    public Queue queueUpdateClientToAuth() {
        return new Queue(RabbitMqSetting.QUEUE_UPDATE_CLIENT_TO_AUTHMODULE.getValue());
    }
    @Bean
    public FanoutExchange fanoutExchangeUpdateClient() {
        return new FanoutExchange(RabbitMqSetting.EXCHANGE_UPDATE_CLIENT.getValue());
    }
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queueUpdateClientToClient()).to(fanoutExchangeUpdateClient());
    }
    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queueUpdateClientToAuth()).to(fanoutExchangeUpdateClient());
    }



    @Bean
    public Queue queueShopClientFindAll() {
        return new Queue(RabbitMqSetting.QUEUE_FIND_ALL_CLIENT.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeShopClientFindAll(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_FIND_ALL_CLIENT.getValue());
    }
    @Bean
    public Binding bindingShopClientFindAll(Queue queueShopClientFindAll, TopicExchange topicExchangeShopClientFindAll){
        return BindingBuilder.bind(queueShopClientFindAll).to(topicExchangeShopClientFindAll).with(RabbitMqSetting.ROUTING_KEY_FIND_ALL_CLIENT.getValue());
    }
}
