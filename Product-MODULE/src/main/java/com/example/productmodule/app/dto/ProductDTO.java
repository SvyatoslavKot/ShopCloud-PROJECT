package com.example.productmodule.app.dto;

import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductDTO implements Serializable {
    private Long id;
    private  String title;
    private BigDecimal price;
    private List<Category> categories;
    private Long count_in_stock;

    public ProductDTO(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categories = product.getCategories();
        this.count_in_stock = product.getCount_in_stock();
    }


}
