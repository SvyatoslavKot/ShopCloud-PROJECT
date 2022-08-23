package com.example.shop_module.mapper;

import com.example.shop_module.domain.Order;
import com.example.shop_module.domain.OrderStatus;
import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    private final UserService userService;

    public OrderMapper(UserService userService) {
        this.userService = userService;
    }

    public OrderDTO toDto(Order order){
        return   OrderDTO.builder()
                .id(order.getId())
                .created(order.getCreated().toString())
                .update(order.getUpdate().toString())
                .userMail(order.getUser().getMail())
                .sum(order.getSum())
                .address(order.getAddress())
                .status(order.getStatus().name())
                .orderDetails(order.getOrders_details())
                .build();
    }

    public Order fromDTO (OrderDTO dto) {
        return  Order.builder()
                .id(dto.getId())
                .created(LocalDateTime.parse(dto.getCreated()))
                .update(LocalDateTime.parse(dto.getUpdate()))
                .user(userService.finByMail(dto.getUserMail()))
                .sum(dto.getSum())
                .address(dto.getAddress())
                .status(OrderStatus.NEW)
                .orders_details(dto.getOrderDetails())
                .build()
                ;
    }
}
