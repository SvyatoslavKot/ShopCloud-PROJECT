package com.example.shopclient_module.rabbitMq.producers;

import com.example.shopclient_module.domain.ShopClient;
import com.example.shopclient_module.dto.UserAuthDto;
import com.example.shopclient_module.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

  //  @Value("${rabbitmq.exchange.newclient}")
    private String exchange = "topic-exchange-auth-new-client";

   // @Value("${rabbitmq.routingkey.authNewClient}")
    private String routingKey = "routingKey-auth-new-client";

    //@Value("${rabbitmq.queue.shopclientNew}")
    private String queue ="queue-auth-new-client";


    @Bean
    public Queue queueAuthNewClient() {
        return new Queue(queue, false);
    }
    @Bean
    public TopicExchange topicExchangeAuthNewClient(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingAuthNewClient(Queue queueAuthNewClient, TopicExchange topicExchangeAuthNewClient){
        return BindingBuilder.bind(queueAuthNewClient).to(topicExchangeAuthNewClient).with(routingKey);
    }

    public void newClientEvent(UserDto user){
        rabbitTemplate.setExchange(exchange);
        log.info("Producer Product id -> " + user);
        rabbitTemplate.convertAndSend(routingKey, user);
    }
}
