package com.example.shop_module.app.service.abstraction;

import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    ResponseEntity getAll();
    ResponseEntity getById(Long id) throws ResponseMessageException;
    ResponseEntity getByParam(Optional<Integer> page,
                              Optional<Integer> size,
                              Optional<String> title,
                              Optional<BigDecimal> min,
                              Optional<BigDecimal> max);

    void addToUserBucket(Long productId, String mail);
    void removeFromBucket(Long productId, String mail);
    void addProduct(ProductDTO productDTO);
    void updateProduct(ProductDTO updateProduct);
    void addProductFromFile (String name, MultipartFile file);


}
