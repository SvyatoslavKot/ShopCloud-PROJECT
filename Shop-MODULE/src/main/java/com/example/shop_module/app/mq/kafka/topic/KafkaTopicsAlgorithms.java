package com.example.shop_module.app.mq.kafka.topic;

import com.example.shop_module.app.mq.kafka.KafkaSettings;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicsAlgorithms {

    @Bean
    public NewTopic topic_algorithms_sort_bubble() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_ALGORITHMS_SORT_BUBBLE.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic_algorithms_sort_quick() {
        return TopicBuilder
                .name(KafkaSettings.TOPIC_ALGORITHMS_SORT_QUICK.getValue())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
