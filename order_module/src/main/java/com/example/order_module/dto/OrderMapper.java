package com.example.order_module.dto;

import com.example.order_module.domain.Order;
import com.example.order_module.domain.OrdersDetails;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public OrderDTO toOrderDto (Order order) {
        return OrderDTO.builder()
                        .id(order.getId())
                        .address(order.getAddress())
                        .userMail(order.getUser_mail())
                        .sum(order.getSum())
                        .delivery(order.getDeliveryType().name())
                        .status(order.getStatus().name())
                        .orderDetails(toOrderDetailsDto(order.getOrders_details()))
                        .build();
    }

    private List<OrderDetailsDto> toOrderDetailsDto (List<OrdersDetails> ordersDetails) {
        List <OrderDetailsDto> orderDetailsDto = ordersDetails.stream()
                .map(orderDetails -> toDetailsDto(orderDetails)).collect(Collectors.toList());
        return orderDetailsDto;
    }

    private OrderDetailsDto toDetailsDto(OrdersDetails details) {
        OrderDetailsDto dto = OrderDetailsDto.builder()
                .orderId(details.getOrder().getId())
                .productId(details.getProduct().getId())
                .title(details.getProduct().getTitle())
                .amount(details.getAmount())
                .price(details.getPrice())
                .build();
        return dto;
    }
}
