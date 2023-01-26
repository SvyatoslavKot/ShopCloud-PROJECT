package com.example.productmodule.app.mq.kafka;


import com.example.productmodule.app.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class KafkaListenerShopModule {



    //@KafkaListener(topics = "productModule-add-bucket-by-mail",groupId = "Kafka_Product")
    //без десериализатора
    public void addToBucket2(String msg)  {
        JSONObject jsonObject = new JSONObject(msg);
        jsonObject.get("mail");
        jsonObject.get("productId");
        log.info("request ->{}, {}", jsonObject.get("mail"), jsonObject.get("productId"));
        //clientService.updateClient(userDto);

    }

    @KafkaListener(topics = "productModule-add-bucket-by-mail", containerFactory = "kafkaListenerContainerFactoryMap")
    public void addToBucket(Map<String, Object> map)  {
        log.info("request ->{}, {}", map.get("mail"), map.get("productId"));
        //clientService.updateClient(userDto);

    }

    @KafkaListener(topics = "productModule-update-product", containerFactory = "kafkaListenerContainerFactoryProductDto")
    public void updateProduct(ProductDTO productDTO)  {
        log.info("request ->{}", productDTO);
        //clientService.updateClient(userDto);

    }
}
