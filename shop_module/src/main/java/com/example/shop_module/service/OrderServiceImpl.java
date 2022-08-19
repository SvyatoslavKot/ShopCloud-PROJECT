package com.example.shop_module.service;

import com.example.shop_module.domain.Order;
import com.example.shop_module.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
