package com.example.shop_module.app.mq.kafka.topic;

import com.example.shop_module.app.mq.kafka.KafkaSettings;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicsProductModule {

    @Bean
    public NewTopic Topic() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_PRODUCT_MODULE_GET_BY_ID.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
