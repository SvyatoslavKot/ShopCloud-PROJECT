package com.example.productmodule.service;

import com.example.productmodule.domain.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
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
}
