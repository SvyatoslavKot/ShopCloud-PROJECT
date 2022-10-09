package com.example.order_module.service;

import com.example.order_module.domain.Order;
import com.example.order_module.domain.OrdersDetails;
import com.example.order_module.dto.OrderDTO;
import com.example.order_module.dto.OrderDetailsDto;

public interface OrderService {

    Order saveOrder (OrderDTO orderDTO);
    Order createOrder (OrderDTO orderDTO);
    OrdersDetails createDetails (OrderDetailsDto orderDetailsDto, Order order);
}
