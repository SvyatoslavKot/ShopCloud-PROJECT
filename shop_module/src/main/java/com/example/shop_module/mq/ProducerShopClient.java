package com.example.shop_module.mq;


import com.example.shop_module.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProducerShopClient {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitSettings settings;

    @Value("${rabbitmq.exchange.shopclientNew}")
    private String exchange;
    @Value("${rabbitmq.routingkey.shopclientNew}")
    private String routingKey;
    @Value("${rabbitmq.queue.shopclientNew}")
    private String queue;

    @Value("${rabbitmq.exchange.shopclientGetClient}")
    private String exchangeGetClient;
    @Value("${rabbitmq.routingkey.shopclientGetClient}")
    private String routingKeyGetClient;
    @Value("${rabbitmq.queue.shopclientGetClient}")
    private String queueGetClient;


    @Bean
    public Queue queueShopclien() {
        return new Queue(queue, false);
    }
    @Bean
    public TopicExchange topicExchangeShopclien(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingShopclien(Queue queueShopclien, TopicExchange topicExchangeShopclien){
        return BindingBuilder.bind(queueShopclien).to(topicExchangeShopclien).with(routingKey);
    }

    public UserDTO newClientMsg(UserDTO user){
        rabbitTemplate.setExchange(exchange);
        log.info("Producer Product id -> " + user);
        UserDTO response =  rabbitTemplate.convertSendAndReceiveAsType(routingKey, user, ParameterizedTypeReference.forType(UserDTO.class));
        log.info("return -> " + response);
        return response;
    }

    @Bean
    public Queue queueShopcliengetClient() {
        return new Queue(queueGetClient, false);
    }
    @Bean
    public TopicExchange topicExchangeShopclienGetClient(){
        return new TopicExchange(exchangeGetClient);
    }

    @Bean
    public Binding bindingShopclienGetClient(Queue queueShopcliengetClient, TopicExchange topicExchangeShopclienGetClient){
        return BindingBuilder.bind(queueShopcliengetClient).to(topicExchangeShopclienGetClient).with(routingKeyGetClient);
    }

    public UserDTO getClientByMail(String mail){
        rabbitTemplate.setExchange(exchangeGetClient);
        UserDTO response = rabbitTemplate.convertSendAndReceiveAsType(routingKeyGetClient, mail, ParameterizedTypeReference.forType(UserDTO.class));
        return response;
    }

    @Bean
    public Queue queueUpdateClientToClient() {
        return new Queue(settings.getQueueUpdateClientToClientmodule());
    }

    @Bean
    public Queue queueUpdateClientToAuth() {
        return new Queue(settings.getQueueUpdateClientToAuthmodule());
    }

    @Bean
    public FanoutExchange fanoutExchangeUpdateClient() {
        return new FanoutExchange(settings.getExchangeUpdateClient());
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queueUpdateClientToClient()).to(fanoutExchangeUpdateClient());
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queueUpdateClientToAuth()).to(fanoutExchangeUpdateClient());
    }

    public void updateClient(UserDTO userDTO){
        rabbitTemplate.setExchange(settings.getExchangeUpdateClient());
        System.out.println(userDTO);
        rabbitTemplate.convertAndSend( userDTO);
    }



}
