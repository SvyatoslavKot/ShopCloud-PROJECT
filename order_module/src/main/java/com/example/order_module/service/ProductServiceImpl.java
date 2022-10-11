package com.example.order_module.service;

import com.example.order_module.domain.Category;
import com.example.order_module.domain.Product;
import com.example.order_module.dto.ProductDTO;
import com.example.order_module.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getId(),
                productDTO.getTitle(),
                productDTO.getPrice());

        productRepository.save(product);
    }
}
