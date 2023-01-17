package com.example.shopclient_module.app.mq.rabbitMq.listener;

import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.mq.ProductModuleListener;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import com.example.shopclient_module.app.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RabbitProductModuleListener implements ProductModuleListener {

    private final ClientShopRepository repository;
    private final ClientService clientService;

    public RabbitProductModuleListener(ClientShopRepository repository, ClientService clientService) {
        this.repository = repository;
        this.clientService = clientService;
    }

    @RabbitListener(queues = "queue-addBucketClient")
    @Override
    public void updateClientBucket(@Payload Map<String , Object> msgMap ){
        String mail = (String) msgMap.get("mail");
        Integer bucketId = (Integer) msgMap.get("bucketId");
        try{
            clientService.addBucket(mail, Long.valueOf(bucketId));
        }catch (RuntimeException e){
            log.error(e.getMessage(), e.getCause());

        }

    }
}
