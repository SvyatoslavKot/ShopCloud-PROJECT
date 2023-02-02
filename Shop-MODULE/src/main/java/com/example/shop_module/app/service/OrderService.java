package com.example.shop_module.app.service;

import com.example.shop_module.app.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity getOrderByMail(String mail);
    ResponseEntity createOrder(OrderDTO orderDTO);
    ResponseEntity getOrderById(Long id);
    ResponseEntity updateOrder(OrderDTO orderDTO);

    void cancelOrder(Long id);
}
