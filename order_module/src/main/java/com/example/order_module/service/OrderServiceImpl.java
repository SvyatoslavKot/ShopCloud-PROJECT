package com.example.order_module.service;

import com.example.order_module.domain.*;
import com.example.order_module.dto.OrderDTO;
import com.example.order_module.dto.OrderDetailsDto;
import com.example.order_module.repository.OrderDetailsRepository;
import com.example.order_module.repository.OrderRepository;
import com.example.order_module.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order order = saveOrder(orderDTO);
        List <OrdersDetails> ordersDetails = orderDTO.getOrderDetails().stream()
               .map(orderDetailsDto -> createDetails(orderDetailsDto, order)).collect(Collectors.toList());
        order.setOrders_details(ordersDetails);
        return orderRepository.save(order);
    }

    @Override
    public OrdersDetails createDetails(OrderDetailsDto dto, Order order) {
        OrdersDetails ordersDetails = OrdersDetails.builder()
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .product(productRepository.findById(dto.getProductId()).get())
                .order(order)
                .build();
        return orderDetailsRepository.save(ordersDetails);
    }

    @Override
    public Order saveOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                        .address(orderDTO.getAddress())
                        .sum(orderDTO.getSum())
                        .status(OrderStatus.CREATED)
                        .deliveryType(DeliveryType.POST)
                        .user_mail(orderDTO.getUserMail())
                        .build();
        orderRepository.save(order);
        return order;
    }

}
