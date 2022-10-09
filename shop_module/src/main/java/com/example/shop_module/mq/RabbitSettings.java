package com.example.shop_module.mq;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RabbitSettings {

    @Value("${rabbitmq.exchange.shopclientUpdateClient}")
    private String exchangeUpdateClient;
    @Value("${rabbitmq.routingkey.shopclientUpdateClient}")
    private String routingKeyUpdateClient;
    @Value("${rabbitmq.queue.shopclientUpdateClientToClientmodule}")
    private String queueUpdateClientToClientmodule;
    @Value("${rabbitmq.queue.shopclientUpdateClientToAuthmodule}")
    private String queueUpdateClientToAuthmodule;

    @Value("${rabbitmq.exchange.productGetById}")
    private String exchangeProductGetById;
    @Value("${rabbitmq.routingkey.productGetById}")
    private String routingKeyProductGetById;
    @Value("${rabbitmq.queue.productGetById}")
    private String queueProductGetById;

    @Value("${rabbitmq.exchange.productAddBucket}")
    private String exchangeProductAddBucket;
    @Value("${rabbitmq.routingkey.productAddBucket}")
    private String routingKeyProductAddBucket;
    @Value("${rabbitmq.queue.productAddBucket}")
    private String queueProductAddBucket;

    @Value("${rabbitmq.exchange.productGetBucket}")
    private String exchangeProductGetBucket;
    @Value("${rabbitmq.routingkey.productGetBucket}")
    private String routingKeyProductGetBucket;
    @Value("${rabbitmq.queue.productGetBucket}")
    private String queueProductGetBucket;

    @Value("${rabbitmq.exchange.productRemoveFromBucket}")
    private String exchangeProductRemoveFromBucket;
    @Value("${rabbitmq.routingkey.productRemoveFromBucket}")
    private String routingKeyProductRemoveFromBucket;
    @Value("${rabbitmq.queue.productRemoveFromBucket}")
    private String queueProductRemoveFromBucket;

    @Value("${rabbitmq.exchange.productConfirmOrder}")
    private String exchangeProductConfirmOrder;
    @Value("${rabbitmq.routingkey.productConfirmOrder}")
    private String routingKeyProductConfirmOrder;
    @Value("${rabbitmq.queue.productConfirmOrder}")
    private String queueProductConfirmOrder;

    @Value("${rabbitmq.exchange.orderCreate}")
    private String exchangeOrderCreate;
    @Value("${rabbitmq.routingkey.orderCreate}")
    private String routingKeyOrderCreate;
    @Value("${rabbitmq.queue.orderCreate}")
    private String queueOrderCreate;

    @Value("${rabbitmq.exchange.orderGet}")
    private String exchangeOrderGet;
    @Value("${rabbitmq.routingkey.orderGet}")
    private String routingKeyOrderGet;
    @Value("${rabbitmq.queue.orderGet}")
    private String queueOrderGet;

    @Value("${rabbitmq.exchange.orderUpdate}")
    private String exchangeOrderUpdate;
    @Value("${rabbitmq.routingkey.orderUpdate}")
    private String routingKeyOrderUpdate;
    @Value("${rabbitmq.queue.orderUpdate}")
    private String queueOrderUpdate;
}
