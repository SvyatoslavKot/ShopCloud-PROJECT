package com.example.shop_module.service;

import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.mapper.ProductMapper;
import com.example.shop_module.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements  ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public List<ProductDTO> getAll() {
        return  productRepository.findAll().stream().map(
                product -> new ProductDTO(
                        product.getId(),
                        product.getTitle(),
                        product.getPrice()
                )).collect(Collectors.toList());
    }
}
