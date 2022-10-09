package com.example.productmodule.dto;

import com.example.productmodule.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderDetailsDto implements Serializable {

    private Long orderId;
    private Long productId;
    private String title;
    private BigDecimal amount;
    private BigDecimal price;

    public OrderDetailsDto( Product product, Long amount) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.amount = new BigDecimal(amount);
        this.price = new BigDecimal(String.valueOf(product.getPrice()));
    }
}