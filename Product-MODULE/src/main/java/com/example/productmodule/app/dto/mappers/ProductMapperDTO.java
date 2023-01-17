package com.example.productmodule.app.dto.mappers;

import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.ProductDTO;

import java.util.ArrayList;

public class ProductMapperDTO {
    public ProductDTO productToDTO (Product product){
        return new ProductDTO().builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .categories(new ArrayList<>(product.getCategories()))
                .count_in_stock(product.getCount_in_stock())
                .build();
    }
    public Product productFromDTO(ProductDTO productDTO){
        return new Product().builder()
                .id(productDTO.getId())
                .title(productDTO.getTitle())
                .price(productDTO.getPrice())
                .categories(productDTO.getCategories())
                .count_in_stock(productDTO.getCount_in_stock())
                .build();
    }
}
