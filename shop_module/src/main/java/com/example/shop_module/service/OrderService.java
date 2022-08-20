package com.example.shop_module.service;

import com.example.shop_module.domain.Order;

public interface OrderService {
    void saveOrder(Order order);
    Order getOrder(Long id);
}
