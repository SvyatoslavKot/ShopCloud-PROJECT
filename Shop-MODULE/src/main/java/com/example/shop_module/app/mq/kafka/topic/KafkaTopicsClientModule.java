package com.example.shop_module.app.mq.kafka.topic;

import com.example.shop_module.app.mq.kafka.KafkaSettings;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicsClientModule {

    @Bean
    public NewTopic clientByMailTopic() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_CLIENT_MODULE_GET_CLIENT_BY_MAIL.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic clientUpdate() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_CLIENT_MODULE_UPDATE_CLIENT.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic clientMessage() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_CLIENT_MODULE_MESSAGE.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }


}
