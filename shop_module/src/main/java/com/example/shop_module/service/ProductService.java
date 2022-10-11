package com.example.shop_module.service;

import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.exceptions.ResponseMessageException;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface ProductService {

    ResponseEntity getAll();
    ResponseEntity getById(Long id) throws ResponseMessageException;
    
    void addToUserBucket(Long productId, String mail);
    void removeFromBucket(Long productId, String mail);
    void addProduct(ProductDTO productDTO);

    ResponseEntity getByParam(Integer page, Integer size, String title, BigDecimal min, BigDecimal max);
}
