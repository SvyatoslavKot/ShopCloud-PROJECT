package com.example.shop_module.app.mq.kafka.topic;

import com.example.shop_module.app.mq.kafka.KafkaSettings;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicsProductModule {

    @Bean
    public NewTopic topic_product_m_GetById() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_PRODUCT_MODULE_GET_BY_ID.getValue())
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic_product_m_addBucketByMail() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_PRODUCT_MODULE_ADD_BUCKET_BY_MAIL.getValue())
                .partitions(2)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topic_product_m_removeFromBucket() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_PRODUCT_MODULE_REMOVE_FROM_BUCKET.getValue())
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic_product_m_updateProduct() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_PRODUCT_MODULE_UPDATE_PRODUCT.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }

}
