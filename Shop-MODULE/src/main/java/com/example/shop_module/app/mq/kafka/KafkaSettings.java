package com.example.shop_module.app.mq.kafka;

public enum KafkaSettings {
    TOPIC_CLIENT_MODULE_GET_CLIENT_BY_MAIL("client-m_get-client-by-mail"),
    TOPIC_REPLY_CLIENT_MODULE_GET_BY_MAIL("client-m_reply_get-client-by-mail"),
    TOPIC_CLIENT_MODULE_UPDATE_CLIENT("client-m_update-client"),
    TOPIC_CLIENT_MODULE_MESSAGE("client-m_message"),
    TOPIC_CLIENT_MODULE_MESSAGE_REQUEST("client-m_message-request"),
    TOPIC_CLIENT_MODULE_MESSAGE_REPLY("client-m_message-reply"),

    TOPIC_PRODUCT_MODULE_GET_BY_ID("productModule-get-by-id"),
    TOPIC_PRODUCT_MODULE_ADD_BUCKET_BY_MAIL("productModule-add-bucket-by-mail"),
    TOPIC_PRODUCT_MODULE_REMOVE_FROM_BUCKET("productModule-remove-from-bucket"),
    TOPIC_PRODUCT_MODULE_UPDATE_PRODUCT("productModule-update-product");


    private String value;

    KafkaSettings(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
