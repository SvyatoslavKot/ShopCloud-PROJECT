package com.example.order_module.dto;

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

    private Long orderId;
    private Long productId;
    private String title;
    private BigDecimal amount;
    private BigDecimal price;

}
