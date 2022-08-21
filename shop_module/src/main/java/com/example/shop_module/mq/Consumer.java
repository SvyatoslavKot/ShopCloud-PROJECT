package com.example.shop_module.mq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @Value("${rabbitmq.queue}")
    private String queue;

    @RabbitListener(queues = "myrabbitmq.queue")
    public void consume(String msg){
        log.info("Consume: " +msg);
        System.out.println("Consume msg -> " + msg);
    }
}
