package com.example.productmodule.rabbitMq;

import com.example.productmodule.domain.Product;
import com.example.productmodule.dto.ProductDTO;
import com.example.productmodule.dto.mappers.ProductMapperDTO;
import com.example.productmodule.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

@Component
@Slf4j
public class ProductListener {


    private ProductService productService;
    private ProductMapperDTO productMapperDTO = new ProductMapperDTO();

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }

    //@RabbitListener(queues = "queue-prItem")
    public void worker8(String message) {
        System.out.println("Listener : " + " message queue -> " +  message);
    }

   @RabbitListener(queues = "queue-prItem")
   @Transactional
    public ProductDTO consume2(@Payload String id) throws InterruptedException{
        log.info("Product id: " + id);
        Product product = productService.getById(Long.parseLong(id)).get();
        log.info("Product: " +  product);
        ProductDTO dto = productMapperDTO.productToDTO(product);
        log.info("Consume DTO -> " + dto);
        return dto;
    }

    // @RabbitListener(queues = "debug")
    public void processMessage2(@Payload String body, @Headers Map<String,Object> headers) {
        System.out.println("body："+body);
        System.out.println("Headers："+headers);
    }

}
