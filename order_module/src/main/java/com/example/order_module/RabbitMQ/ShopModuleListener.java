package com.example.order_module.RabbitMQ;


import com.example.order_module.domain.DeliveryType;
import com.example.order_module.domain.Order;
import com.example.order_module.dto.OrderDTO;
import com.example.order_module.dto.OrderMapper;
import com.example.order_module.repository.OrderRepository;
import com.example.order_module.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ShopModuleListener {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper = new OrderMapper();

    public ShopModuleListener(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "queue-orderCreate")
    @Transactional
    public Map<String, Object> consume2(@Payload OrderDTO dto) {
        Map<String,Object> responseMap = new HashMap<>();
        try{
            Order order = orderService.createOrder(dto);
            responseMap.put("orderId", order.getId());
            return responseMap;
        }catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            responseMap.put("exception" , e.getMessage());
            return responseMap;
        }
    }

    @RabbitListener(queues = "queue-orderGet")
    @Transactional
    public OrderDTO getOrderById(@Payload Long id) {
        if (orderRepository.findById(id).isPresent()) {
            try {
                Order order = orderRepository.findById(id).get();
                OrderDTO orderDTO = orderMapper.toOrderDto(order);
                return orderDTO;
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                e.printStackTrace();
                return new OrderDTO();
            }
        }
        return new OrderDTO();
    }

    @RabbitListener(queues = "queue-orderUpdate")
    @Transactional
    public Map<String, Object> getOrderById(@Payload OrderDTO dto) {
        System.out.println(dto);
        Map<String , Object> responseMap = new HashMap<>();
        if (orderRepository.findById(dto.getId()).isPresent()){
            try{
                Order order = orderRepository.findById(dto.getId()).get();
                if (dto.getAddress()!= null){
                    order.setAddress(dto.getAddress());
                }
                if (dto.getSum()!= null){
                    order.setSum(dto.getSum());
                }
                if (dto.getAddress()!= null){
                    order.setAddress(dto.getAddress());
                }
                if (dto.getUserMail()!= null){
                    order.setUser_mail(dto.getUserMail());
                }
                if (dto.getPhone()!= null){
                    order.setPhone(dto.getPhone());
                }
                if (dto.getDelivery()!= null) {
                    switch (dto.getDelivery()) {
                        case "POST": order.setDeliveryType(DeliveryType.POST);
                            break;
                        case "DHL": order.setDeliveryType(DeliveryType.DHL);
                            break;
                        default:  order.setDeliveryType(null);
                    }
                }
                orderRepository.save(order);
                responseMap.put("OK", "success");
                System.out.println(order);
                return responseMap;
            }catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(), e.getCause());
                responseMap.put("exception", e.getMessage());
                return responseMap;
            }

        }
        responseMap.put("exception", "Order with id " + dto.getId() + " not found.");
        return responseMap;
    }
}
