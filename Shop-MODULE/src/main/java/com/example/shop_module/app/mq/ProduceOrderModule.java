package com.example.shop_module.app.mq;

import com.example.shop_module.app.dto.OrderDTO;

public interface ProduceOrderModule {

    Long createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById (Long id);
    boolean orderUpdate (OrderDTO dto);
    void cancelOrder(Long id);
}
