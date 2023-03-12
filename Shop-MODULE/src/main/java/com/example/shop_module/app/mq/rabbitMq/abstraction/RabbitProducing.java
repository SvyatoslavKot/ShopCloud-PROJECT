package com.example.shop_module.app.mq.rabbitMq.abstraction;

import java.util.List;
import java.util.Map;

public interface RabbitProducing {

    Object convertSendAndReceive(String exchange, String routingKey, Object msg, Class<?> receiveType);
    Map<Object,Object> convertSendAndReceiveMap(String exchange, String routingKey, Object msg);
    public List<?> convertSendAndReceiveList(String exchange, String routingKey, Object msg);
    void produce(String exchange, String routingKey, Object msg);
}
