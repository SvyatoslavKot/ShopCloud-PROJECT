package com.example.shop_module.app.mq.rabbitMq;

import com.example.shop_module.app.mq.rabbitMq.abstraction.RabbitAbstractProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;

public class RabbitProducer extends RabbitAbstractProducer {

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public Object convertSendAndReceive(String exchange, String routingKey, Object msg, Class<?> receiveType){
        rabbitTemplate.setExchange(exchange);
        Object receive =  rabbitTemplate.convertSendAndReceiveAsType(routingKey,msg, ParameterizedTypeReference.forType(receiveType));
        return receive;
    }

    @Override
    public Map<Object,Object> convertSendAndReceiveMap(String exchange, String routingKey, Object msg){
        rabbitTemplate.setExchange(exchange);
        Map<Object, Object> map =  rabbitTemplate.convertSendAndReceiveAsType(routingKey,msg, ParameterizedTypeReference.forType(Map.class));
        return map;
    }

    @Override
    public List<?> convertSendAndReceiveList(String exchange, String routingKey, Object msg){
        rabbitTemplate.setExchange(exchange);
        List<?> list = rabbitTemplate.convertSendAndReceiveAsType(routingKey,msg, ParameterizedTypeReference.forType(List.class));
        return list;
    }

    @Override
    public void produce(String exchange, String routingKey, Object msg) {
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.convertAndSend(routingKey,msg);
    }

    @Override
    public RabbitTemplate getRabbitTemplate() {
        return super.getRabbitTemplate();
    }
}
