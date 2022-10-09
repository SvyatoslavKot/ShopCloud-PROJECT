package com.example.shopclient_module.rabbitMq.listener;

import com.example.shopclient_module.dto.UserDto;
import com.example.shopclient_module.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class ProductModuleListener {

    private final ClientService clientService;

    @RabbitListener(queues = "queue-addBucketClient")
    public void updateClient(@Payload Map<String , Object> msgMap ){
        String mail = (String) msgMap.get("mail");
        Integer bucketId = (Integer) msgMap.get("bucketId");
        clientService.addBucket(mail, Long.valueOf(bucketId));


    }
}
