package com.example.productmodule.dto.mappers;

import com.example.productmodule.domain.Product;
import com.example.productmodule.dto.ProductDTO;

public class ProductMapperDTO {
    public ProductDTO productToDTO (Product product){
        return new ProductDTO().builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .categories(product.getCategories())
                .build();
    }
    public Product productFromDTO(ProductDTO productDTO){
        return new Product().builder()
                .id(productDTO.getId())
                .title(productDTO.getTitle())
                .price(productDTO.getPrice())
                .categories(productDTO.getCategories())
                .build();
    }
}
