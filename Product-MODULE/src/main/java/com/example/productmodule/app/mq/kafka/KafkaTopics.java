package com.example.productmodule.app.mq.kafka;

public enum KafkaTopics {

    PRODUCT_GET_BY_ID("productModule-get-by-id");

    private String value;

    KafkaTopics(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
