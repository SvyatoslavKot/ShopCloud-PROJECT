package com.example.productmodule.service;

import com.example.productmodule.domain.Product;
import com.example.productmodule.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getById(Long id);

    Page<Product> getByParams(Optional<String> nameFilter,
                              Optional<Integer> page,
                              Optional<Integer> size,
                              Optional<BigDecimal> min,
                              Optional<BigDecimal> max,
                              Optional<String> sortField,
                              Optional<String> sortOrder);

    void addProduct(ProductDTO productDTO);
    List<ProductDTO> getAll();

    void addToUserBucket(Long productId, String mail);
    void removeFromBucket(Long productId, String mail);

}
