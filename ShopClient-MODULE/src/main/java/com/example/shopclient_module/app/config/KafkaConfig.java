package com.example.shopclient_module.app.config;

import com.example.shopclient_module.app.dto.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import java.util.HashMap;
import java.util.Map;

import static com.example.shopclient_module.app.config.JsonDeserializer.OBJECT_MAPPER;
import static com.example.shopclient_module.app.config.JsonDeserializer.TYPE_REFERENCE;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private  String kafkaBootstrapServers;

    @Bean
    public KafkaTemplate<String, Object> myKafkaTemplate () {
        return new KafkaTemplate<>(producerFactoryJson());
    }

    @Bean
    public Map<String, Object> consumerConfigJson() {
            Map <String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "group3");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
            props.put(OBJECT_MAPPER, new ObjectMapper());

            return props;
    }

    @Bean
    public Map<String, Object> producerConfigJson() {
        Map <String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG,"1");
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33_544_432);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1_000);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        props.put(OBJECT_MAPPER, new ObjectMapper());

        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactoryJson() {
            return  new DefaultKafkaProducerFactory<>(producerConfigJson());
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactoryUser() {
        consumerConfigJson().put(TYPE_REFERENCE, new TypeReference<UserDto>(){});
            return new DefaultKafkaConsumerFactory<>(consumerConfigJson());

    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactoryString() {
        consumerConfigJson().put(TYPE_REFERENCE, new TypeReference<String>(){});
            return new DefaultKafkaConsumerFactory<>(consumerConfigJson());
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserDto> kafkaListenerContainerFactoryUser() {
            ConcurrentKafkaListenerContainerFactory<String, UserDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactoryUser());
            return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactoryString() {
            ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactoryString());
            return factory;

    }



}
