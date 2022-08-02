package com.example.shop_module.controller;

import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list (Model model){
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "product_page/products";
    }
}
