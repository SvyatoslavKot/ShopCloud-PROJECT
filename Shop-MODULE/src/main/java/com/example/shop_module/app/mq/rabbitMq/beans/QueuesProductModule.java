package com.example.shop_module.app.mq.rabbitMq.beans;

import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueuesProductModule {

    @Bean
    public Queue queueProductGetById() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_GET_BY_ID.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductGetById(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_PRODUCT_GET_BY_ID.getValue());
    }
    @Bean
    public Binding bindingProductGetById(Queue queueProductGetById, TopicExchange topicExchangeProductGetById){
        return BindingBuilder.bind(queueProductGetById).to(topicExchangeProductGetById).with(RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_BY_ID.getValue());
    }



    @Bean
    public Queue queueProductAddBucket() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_ADD_BUCKET.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductAddBucket(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_PRODUCT_ADD_BUCKET.getValue());
    }
    @Bean
    public Binding bindingProductAddBucket(Queue queueProductAddBucket, TopicExchange topicExchangeProductAddBucket){
        return BindingBuilder.bind(queueProductAddBucket).to(topicExchangeProductAddBucket).with(RabbitMqSetting.ROUTING_PRODUCT_ADD_BUCKET.getValue());
    }





    @Bean
    public Queue queueProductGetBucket() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_GET_BUCKET.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductGetBucket(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_PRODUCT_GET_BUCKET.getValue());
    }
    @Bean
    public Binding bindingProductGetBucket(Queue queueProductGetBucket, TopicExchange topicExchangeProductGetBucket){
        return BindingBuilder.bind(queueProductGetBucket).to(topicExchangeProductGetBucket).with(RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_BUCKET.getValue());
    }



    @Bean
    public Queue queueProductRemoveFromBucket() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_REMOVE_FROM_BUCKET.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductRemoveFromBucket(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_PRODUCT_REMOVE_FROM_BUCKET.getValue());
    }
    @Bean
    public Binding bindingProductRemoveFromBucket(Queue queueProductRemoveFromBucket, TopicExchange topicExchangeProductRemoveFromBucket){
        return BindingBuilder.bind(queueProductRemoveFromBucket).to(topicExchangeProductRemoveFromBucket).with(RabbitMqSetting.ROUTING_KEY_PRODUCT_REMOVE_FROM_BUCKET.getValue());
    }




    @Bean
    public Queue queueProductGetAll() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_GET_ALL.getValue() ,false);
    }
    @Bean
    public TopicExchange topicExchangeProductGetAll(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_PRODUCT_GET_ALL.getValue());
    }
    @Bean
    public Binding bindingProductGetAll(Queue queueProductGetAll, TopicExchange topicExchangeProductGetAll){
        return BindingBuilder.bind(queueProductGetAll).to(topicExchangeProductGetAll).with(RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_ALL.getValue());
    }




    @Bean
    public Queue queueProductAdd() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_ADD.getValue());
    }
    @Bean
    public Queue queueForOrdersProductAdd() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_ADD_FOR_ORDER.getValue());
    }
    @Bean
    public FanoutExchange fanoutExchangeProductAdd() {
        return new FanoutExchange(RabbitMqSetting.EXCHANGE_PRODUCT_ADD.getValue());
    }
    @Bean
    public Binding bindingProducts() {
        return BindingBuilder.bind(queueProductAdd()).to(fanoutExchangeProductAdd());
    }
    @Bean
    public Binding bindingOrders() {
        return BindingBuilder.bind(queueForOrdersProductAdd()).to(fanoutExchangeProductAdd());
    }




    @Bean
    public Queue queueProductConfirmOrder() {
        return new Queue(RabbitMqSetting.QUEUE_PRODUCT_CONFIRM_ORDER.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductConfirmOrder(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_PRODUCT_CONFIRM_ORDER.getValue());
    }
    @Bean
    public Binding bindingProductConfirmOrder(Queue queueProductConfirmOrder, TopicExchange topicExchangeProductConfirmOrder){
        return BindingBuilder.bind(queueProductConfirmOrder).to(topicExchangeProductConfirmOrder).with(RabbitMqSetting.ROUTING_KEY_PRODUCT_CONFIRM_ORDER.getValue());
    }
}
