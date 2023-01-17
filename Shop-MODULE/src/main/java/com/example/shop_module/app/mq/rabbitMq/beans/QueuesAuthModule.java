package com.example.shop_module.app.mq.rabbitMq.beans;

import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QueuesAuthModule {

    @Bean
    public Queue queueAuth() {
        return new Queue(RabbitMqSetting.QUEUE_AUTH.getValue(), false);
    }
    @Bean
    public TopicExchange topicExchangeAuth(){
        return new TopicExchange(RabbitMqSetting.EXCHANGE_AUTH.getValue());
    }
    @Bean
    public Binding bindingAuth(Queue queueAuth, TopicExchange topicExchangeAuth){
        return BindingBuilder
                .bind(queueAuth)
                .to(topicExchangeAuth)
                .with(RabbitMqSetting.ROUTING_KEY_AUTH.getValue());
    }
}
