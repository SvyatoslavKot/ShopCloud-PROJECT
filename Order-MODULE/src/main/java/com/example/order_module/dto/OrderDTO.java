package com.example.order_module.dto;

import com.example.order_module.domain.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@ToString
public class OrderDTO implements Serializable {
    private Long id;
    private String userMail;
    private BigDecimal sum;
    private String address;
    private String phone;
    private String delivery;
    private String status;
    private List<OrderDetailsDto> orderDetails;


    public OrderDTO(Order order) {
        this.id = order.getId();
        this.userMail = order.getUser_mail();
        this.sum = order.getSum();
        this.address = order.getAddress();
        this.phone = order.getPhone() != null ? order.getPhone() : "";
        this.delivery = order.getDeliveryType() != null ? order.getDeliveryType().name() : null;
        this.status = order.getStatus().name();
        this.orderDetails = order.getOrders_details().stream()
                .map(ordersDetails -> new OrderDetailsDto(ordersDetails))
                .collect(Collectors.toList());
    }
}
