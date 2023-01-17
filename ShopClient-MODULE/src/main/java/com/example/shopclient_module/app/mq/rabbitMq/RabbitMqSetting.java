package com.example.shopclient_module.app.mq.rabbitMq;

public enum RabbitMqSetting {

    QUEUE_LISTEN_SHOP_CLIENT_NEW("queue-shopclientNew"),
    QUEUE_LISTEN_SHOP_CLIENT_GET_USER("queue-shopclientGetUser"),
    QUEUE_LISTEN_SHOP_CLIENT_UPDATE_CLIENT("queue-shopclientUpdateClient"),

    QUEUE_AUTH_MODULE_NEW_CLIENT("queue-auth-new-client"),
    EXCHANGE_AUTH_MODULE_NEW_CLIENT("topic-exchange-auth-new-client"),
    ROUTING_KEY_AUTH_MODULE_NEW_CLIENT("routingKey-auth-new-client");

    private String value;

    RabbitMqSetting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
