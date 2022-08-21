package com.example.rabbit;

import com.example.rabbit.config.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@ComponentScan
//@Import(RabbitConfig.class)
public class Example1 {
    public static void main(String[] args) {
        SpringApplication.run(Example1.class, args);
    }
}
