package com.example.productmodule.dto;

import com.example.productmodule.domain.Category;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO implements Serializable {
    private Long id;
    private  String title;
    private BigDecimal price;
    private List<Category> categories;

    public ProductDTO(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }
}
