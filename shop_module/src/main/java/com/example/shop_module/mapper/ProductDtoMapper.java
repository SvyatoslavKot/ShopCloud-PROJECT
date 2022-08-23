package com.example.shop_module.mapper;

import com.example.shop_module.domain.Product;
import com.example.shop_module.dto.ProductDTO;

public class ProductDtoMapper {

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
