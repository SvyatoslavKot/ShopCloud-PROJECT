package com.example.order_module.RabbitMQ;

import com.example.order_module.dto.ProductDTO;
import com.example.order_module.dto.OrderRequest;
import com.example.order_module.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class ProductModuleListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;


    @RabbitListener(queues = "queue_order-m_confirm-order-from-bucket")

    public void orderCreate(@Payload OrderRequest request) {
        try{
            List<ProductDTO> productDTOS = request.getProductDTOS();
            String mail = request.getUserMail();
            orderService.createOrder(productDTOS,mail);
        }catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            log.error(e.getLocalizedMessage());
            log.info("Exception createOrder ->" + e.getMessage());
        }
    }
}
