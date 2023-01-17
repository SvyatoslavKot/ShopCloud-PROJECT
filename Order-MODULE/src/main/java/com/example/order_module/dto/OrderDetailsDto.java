package com.example.order_module.dto;

import com.example.order_module.domain.OrdersDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonIgnoreProperties
@ToString
public class OrderDetailsDto implements Serializable {

    private Long id;
    private Long productId;
    private String title;
    private BigDecimal amount;
    private BigDecimal price;

    public OrderDetailsDto(OrdersDetails orderDetails) {
        this.id = orderDetails.getId();
        this.productId = orderDetails.getProduct_id();
        this.title = orderDetails.getTitle();
        this.amount = orderDetails.getAmount();
        this.price = orderDetails.getPrice();
    }
}
