package com.example.shop_module.service;

import com.example.shop_module.config.OrderIntegrationConfig;
import com.example.shop_module.domain.Order;
import com.example.shop_module.dto.OrderIntegrationDTO;
import com.example.shop_module.mq.Producer;
import com.example.shop_module.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderIntegrationConfig integrationConfig;

    @Autowired
    Producer producer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderIntegrationConfig integrationConfig) {
        this.orderRepository = orderRepository;
        this.integrationConfig = integrationConfig;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order saveOrder = orderRepository.save(order);
        System.out.println("*****************************************");
        producer.sendOrder(order);
        //sendIntegrationNotify(saveOrder);
    }


    //@Transactional
    public void sendIntegrationNotify(Order order){
            OrderIntegrationDTO dto = new OrderIntegrationDTO();
            dto.setUsername(order.getUser().getMail());
            dto.setAddress(order.getAddress());
            dto.setOrderId(order.getId());
            List<OrderIntegrationDTO.OrderDetailsDTO> details = order.getOrders_details().stream()
                    .map(OrderIntegrationDTO.OrderDetailsDTO::new).collect(Collectors.toList());
            dto.setDetails(details);
            System.out.println("********" +
                    dto +
                    "*************");
            Message<OrderIntegrationDTO> message = MessageBuilder.withPayload(dto)
                    .setHeader("Content-Type", "application/json")
                    .build();


            integrationConfig.getOrdersChannel().send(message);

    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
