package com.example.shop_module.mq;

import com.example.shop_module.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ProduserAuthModule {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.AUTH-login}")
    private String exchangeAuth;

    @Value("${rabbitmq.routingkey.AUTH-login}")
    private String routingKeyAuth;

    @Value("${rabbitmq.routingkey.AUTH-login}")
    private String queueAuth;

    @Bean
    public Queue queueAuth() {
        return new Queue(queueAuth, false);
    }
    @Bean
    public TopicExchange topicExchangeAuth(){
        return new TopicExchange(exchangeAuth);
    }

    @Bean
    public Binding bindingAuth(Queue queueAuth, TopicExchange topicExchangeAuth){
        return BindingBuilder.bind(queueAuth).to(topicExchangeAuth).with(routingKeyAuth);
    }

    public Map<Object, Object> authorization(String email, String password){
        rabbitTemplate.setExchange(exchangeAuth);
        HashMap<String,String> requestMap = new HashMap<>();
        requestMap.put("password", password);
        requestMap.put("mail", email);
        Map<Object, Object> response = rabbitTemplate.convertSendAndReceiveAsType(routingKeyAuth, requestMap, ParameterizedTypeReference.forType(Map.class));
        return response;
    }
}
