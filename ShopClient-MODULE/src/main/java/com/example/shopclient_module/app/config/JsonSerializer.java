package com.example.shopclient_module.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonSerializer <T> implements Serializer {
    public static final String OBJECT_MAPPER = "objectMapper";
    private final String encoding = StandardCharsets.UTF_8.name();
    private ObjectMapper objectMapper;


    @Override
    public void configure(Map configs, boolean isKey) {
        objectMapper = (ObjectMapper) configs.get(OBJECT_MAPPER);
        if (objectMapper == null) {
            throw new IllegalArgumentException("config property OBJECT_MAPPER was not set");
        }
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        try {
            if (data == null) {
                return null;
            } else {
                return objectMapper.writeValueAsString(data).getBytes(encoding);
            }
        } catch (Exception e) {
            throw new SerializationException("Error when serializing StringValue to byte[] ", e);
        }
    }
}