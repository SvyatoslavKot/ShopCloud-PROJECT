package com.example.order_module.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@Getter
@Setter
@Builder
@ToString
public class ProductDTO implements Serializable {
    private Long id;
    private  String title;
    private BigDecimal price;
    private Long count_in_stock;

    public ProductDTO(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }
}