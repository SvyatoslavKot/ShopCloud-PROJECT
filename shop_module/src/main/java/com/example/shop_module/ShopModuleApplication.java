package com.example.shop_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ShopModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopModuleApplication.class, args);
    }

}
