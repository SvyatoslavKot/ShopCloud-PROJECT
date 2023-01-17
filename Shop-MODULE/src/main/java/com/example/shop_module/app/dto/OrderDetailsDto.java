package com.example.shop_module.app.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class OrderDetailsDto implements Serializable {

    private Long id;
    private Long productId;
    private String title;
    private BigDecimal amount;
    private BigDecimal price;

}
