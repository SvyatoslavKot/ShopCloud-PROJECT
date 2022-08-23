package com.example.shop_module.mq;

import com.example.shop_module.domain.Order;
import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class Consumer {

    @Value("${rabbitmq.queue}")
    private String queue;

    @Autowired
    private OrderMapper orderMapper;

    @RabbitListener(queues = "myrabbitmq.queue-shop")
    public void consume2(@Payload OrderDTO dto){
        Order order = orderMapper.fromDTO(dto);
        log.info("Consume: " +  dto.getId());
        System.out.println("Consume msg -> " + order);
    }

   // @RabbitListener(queues = "debug")
    public void processMessage2(@Payload String body, @Headers Map<String,Object> headers) {
        System.out.println("body："+body);
        System.out.println("Headers："+headers);
    }
}
