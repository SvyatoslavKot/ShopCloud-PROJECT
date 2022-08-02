package com.example.shop_module.mapper;

import com.example.shop_module.domain.Product;
import com.example.shop_module.dto.ProductDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDTO dto);

    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    List<Product> toProductList (List<ProductDTO> productDTOS);

    List<ProductDTO> fromProductList(List<Product> products);
}
