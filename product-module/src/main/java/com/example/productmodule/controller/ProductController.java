package com.example.productmodule.controller;


import com.example.productmodule.domain.Product;
import com.example.productmodule.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//swagger @Tag(name = "User Api", description = "API to manipulate user resource")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/list", produces ="application/json")
    public Page<Product> getByParam( @RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                                     @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                                     @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                                     @RequestParam(name = "page", required = false) Optional<Integer> page,
                                     @RequestParam(name = "size", required = false) Optional<Integer> size,
                                     @RequestParam(name = "sortField", required = false) Optional<String> sortField,
                                     @RequestParam(name = "sortOrder", required = false) Optional<String> sortOrder){
        return productService.getByParams(
                titleFilter,
                page,
                size,
                min,
                max,
                sortField,
                sortOrder);}
}
