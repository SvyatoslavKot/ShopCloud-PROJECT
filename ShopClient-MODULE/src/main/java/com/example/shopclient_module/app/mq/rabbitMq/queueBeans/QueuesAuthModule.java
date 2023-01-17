package com.example.shopclient_module.app.mq.rabbitMq.queueBeans;

import com.example.shopclient_module.app.mq.rabbitMq.RabbitMqSetting;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueuesAuthModule {

    @Bean
    public Queue queueAuthNewClient() {
        return new Queue(RabbitMqSetting.QUEUE_AUTH_MODULE_NEW_CLIENT.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeAuthNewClient(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_AUTH_MODULE_NEW_CLIENT.getValue());
    }
    @Bean
    public Binding bindingAuthNewClient(Queue queueAuthNewClient, TopicExchange topicExchangeAuthNewClient){
        return BindingBuilder.bind(queueAuthNewClient).to(topicExchangeAuthNewClient).with(RabbitMqSetting.ROUTING_KEY_AUTH_MODULE_NEW_CLIENT.getValue());
    }

}
