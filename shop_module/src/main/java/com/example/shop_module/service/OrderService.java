package com.example.shop_module.service;

import com.example.shop_module.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity createOrder(OrderDTO orderDTO);
    void cancelOrder(Long id);
    ResponseEntity getOrderById(Long id);
    ResponseEntity updateOrder(OrderDTO orderDTO);
}
