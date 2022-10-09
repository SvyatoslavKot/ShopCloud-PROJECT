package com.example.shop_module.service;

import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();
    void addToUserBucket(Long productId, String mail);

    void removeFromBucket(Long productId, String mail);
    void addProduct(ProductDTO productDTO);

    ResponseEntity getById(Long id) throws ResponseMessageException;


}
