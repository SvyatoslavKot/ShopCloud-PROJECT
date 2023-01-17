package com.example.shop_module.app.mq.rabbitMq;

public enum RabbitMqSetting {

    QUEUE_AUTH("queue-auth_login"),
    ROUTING_KEY_AUTH("routingKey-auth_login"),
    EXCHANGE_AUTH("topic-exchange-auth_login"),

    QUEUE_GET_CLIENT("queue-shopclientGetUser"),
    ROUTING_KEY_GET_CLIENT("routingKey-shopclientGetClient"),
    EXCHANGE_GET_CLIENT("topic-exchange-shopclientGetClient"),

    QUEUE_SHOPCLIENT_NEW("queue-shopclientNew"),
    ROUTING_KEY_SHOPCLIENT_NEW("routingKey-shopclientNew"),
    EXCHANGE_SHOPCLIENT_NEW("topic-exchange-shopclientNew"),

    QUEUE_UPDATE_CLIENT_TO_CLIENTMODULE("queue-shopclientUpdateClient"),
    QUEUE_UPDATE_CLIENT_TO_AUTHMODULE("queue-shopclientUpdateClientAuth"),
    ROUTING_KEY_UPDATE_CLIENT("routingKey-shopclientUpdateClient"),
    EXCHANGE_UPDATE_CLIENT("fanout-exchange-updateclient"),

    QUEUE_FIND_ALL_CLIENT("queue-shop_client-find-all"),
    ROUTING_KEY_FIND_ALL_CLIENT("routingKey-shop_client-find-all"),
    EXCHANGE_FIND_ALL_CLIENT("esxchange-shop_client-find-all"),

    QUEUE_PRODUCT_GET_BY_ID("queue-productGet"),
    EXCHANGE_PRODUCT_GET_BY_ID("topic-exchange-productGet"),
    ROUTING_KEY_PRODUCT_GET_BY_ID("routingKey-productsGet"),

    QUEUE_PRODUCT_ADD_BUCKET("queue-prAddBucket"),
    EXCHANGE_PRODUCT_ADD_BUCKET("topic-exchange-prAddBucket"),
    ROUTING_PRODUCT_ADD_BUCKET("routingKey-prAddBucket"),

    QUEUE_PRODUCT_GET_BUCKET("queue-prGetBucket"),
    EXCHANGE_PRODUCT_GET_BUCKET("topic-exchange-prGetBucket"),
    ROUTING_KEY_PRODUCT_GET_BUCKET("routingKey-prGetBucket"),

    QUEUE_PRODUCT_REMOVE_FROM_BUCKET("queue-prRemoveFromBucket"),
    EXCHANGE_PRODUCT_REMOVE_FROM_BUCKET("topic-exchange-prRemoveFromBucket"),
    ROUTING_KEY_PRODUCT_REMOVE_FROM_BUCKET("routingKey-prRemoveFromBucket"),

    QUEUE_PRODUCT_GET_ALL("queue-prGetAll"),
    EXCHANGE_PRODUCT_GET_ALL("topic-exchange-prGetAll"),
    ROUTING_KEY_PRODUCT_GET_ALL("routingKey-prGetAll"),

    QUEUE_PRODUCT_ADD_FOR_ORDER("queue-prAddForOrder"),
    QUEUE_PRODUCT_ADD("queue-prAdd"),
    ROUTING_KEY_PRODUCT_ADD("routingKey-prAdd"),
    EXCHANGE_PRODUCT_ADD("topic-exchange-prAdd"),

    QUEUE_PRODUCT_CONFIRM_ORDER("queue-prConfirmOrder"),
    ROUTING_KEY_PRODUCT_CONFIRM_ORDER("routingKey-prConfirmOrder"),
    EXCHANGE_PRODUCT_CONFIRM_ORDER("topic-exchange-prConfirmOrder"),

    QUEUE_ORDER_CREATE("queue-orderCreate"),
    ROUTING_KEY_ORDER_CREATE("routingKey-orderCreate"),
    EXCHANGE_ORDER_CREATE("topic-exchange-orderCreate"),

    QUEUE_ORDER_GET("queue-orderGet"),
    ROUTING_KEY_ORDER_GET("routingKey-orderGet"),
    EXCHANGE_ORDER_GET("topic-exchange-orderGet"),

    QUEUE_ORDER_UPDATE("queue-orderUpdate"),
    ROUTING_KEY_ORDER_UPDATE("routingKey-orderUpdate"),
    EXCHANGE_ORDER_UPDATE("topic-exchange-orderUpdate");


    private String value;

    RabbitMqSetting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
