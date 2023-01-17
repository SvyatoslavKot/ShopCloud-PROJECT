package com.example.order_module.service;

import com.example.order_module.dto.OrderDTO;
import com.example.order_module.dto.ProductDTO;

import java.util.List;

public interface OrderService {

    void createOrder (List<ProductDTO> productDTOList, String mail);
    List<OrderDTO> getOrdersByMail (String mail);
    OrderDTO getOrderById (Long id);
}
