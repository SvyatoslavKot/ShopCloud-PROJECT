package com.example.shop_module.app.service;

import com.example.shop_module.app.service.abstraction.*;
import com.example.shop_module.app.service.kafkaService.KafkaAlgorithmsService;
import com.example.shop_module.app.service.kafkaService.KafkaClientService;
import com.example.shop_module.app.service.kafkaService.KafkaProductService;
import com.example.shop_module.app.service.rabbitMqService.RabbitProductService;
import com.example.shop_module.app.service.rabbitMqService.RabbitAuthService;
import com.example.shop_module.app.service.rabbitMqService.RabbitBucketService;
import com.example.shop_module.app.service.rabbitMqService.RabbitOrderService;
import com.example.shop_module.app.service.rabbitMqService.RabbitUserService;
import com.example.shop_module.app.service.restService.HttpClientBucketService;
import com.example.shop_module.app.service.restService.HttpClientOrderService;
import com.example.shop_module.app.service.restService.HttpClientProductService;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.service.restService.WebClientClientShopService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

public class ServiceFactory {

    public static ProductService newProductService(RabbitTemplate rabbitTemplate, SimpMessagingTemplate templateMsg){
        return new RabbitProductService(rabbitTemplate, templateMsg);
    }
    public  static ProductService newProductService(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        return  new HttpClientProductService(restTemplate, httpClientSettings);
    }
    public static ProductService newProductService(KafkaTemplate kafkaTemplate) {
        return new KafkaProductService(kafkaTemplate);
    }


    public static UserService newUserService(KafkaTemplate kafkaTemplate) {
        return new KafkaClientService(kafkaTemplate);
    }
    public static UserService newUserService(RabbitTemplate rabbitTemplate) {
        return new RabbitUserService(rabbitTemplate);
    }
    public static UserService newUserService(WebClient.Builder webBuilder, HttpClientSettings httpClientSettings) {
        return new WebClientClientShopService(webBuilder,httpClientSettings);
    }


    public static BucketService newBucketService(RabbitTemplate rabbitTemplate) {
        return  new RabbitBucketService(rabbitTemplate);
    }
    public static BucketService newBucketService(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        return new HttpClientBucketService(restTemplate, httpClientSettings);
    }



    public static OrderService newOrderService(RabbitTemplate rabbitTemplate) {
        return new RabbitOrderService(rabbitTemplate);
    }
    public static OrderService newOrderService(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        return  new HttpClientOrderService(restTemplate, httpClientSettings);
    }


    public static AuthService newAuthService(RabbitTemplate rabbitTemplate) {
        return new RabbitAuthService(rabbitTemplate);
    }
    public static AlgorithmsService newAlgorithmsService(KafkaTemplate kafkaTemplate) {
        return  new KafkaAlgorithmsService(kafkaTemplate);
    }



}
