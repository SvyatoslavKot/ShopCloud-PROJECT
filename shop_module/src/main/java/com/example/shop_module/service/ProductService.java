package com.example.shop_module.service;

import com.example.shop_module.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String mail);
    void removeFromBucket(Long productId, String mail);


}
