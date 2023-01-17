package com.example.shop_module;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class ShopModuleApplication {

    public static void main(String[] args) {
        log.info("=> => => =>   A P P L I C A T I O N  S H O P _ M O D U L E  S T A R T   <= <= <= <=");
        SpringApplication.run(ShopModuleApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}
