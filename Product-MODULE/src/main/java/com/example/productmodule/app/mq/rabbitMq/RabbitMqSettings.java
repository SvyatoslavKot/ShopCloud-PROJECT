package com.example.productmodule.app.mq.rabbitMq;

public enum RabbitMqSettings {
    QUEUE_CLIENT_MODULE_ADD_BUCKET_CLIENT("queue-addBucketClient"),
    EXCHANGE_CLIENT_MODULE_ADD_BUCKET_CLIENT("topic-exchange-addBucketClient"),
    ROUTIN_KEY_CLIENT_MODULE_ADD_BUCKET_CLIENT("routingKey-addBucketClient"),

    QUEUE_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET("queue_order-m_confirm-order-from-bucket"),
    EXCHANGE_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET("topic-exchange_order-m_confirm-order-from-bucket"),
    ROUTIN_KEY_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET("routingKey_order-m_confirm-order-from-bucket");

    private String value;

    RabbitMqSettings(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
