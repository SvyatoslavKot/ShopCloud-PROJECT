package com.example.shop_module.controller;

import com.example.shop_module.aopService.MeasureMethod;
import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.service.ProductService;
import com.example.shop_module.service.SessionObjectHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;

    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder) {
        this.productService = productService;
        this.sessionObjectHolder = sessionObjectHolder;
    }
    @MeasureMethod
    @GetMapping
    public String list (Model model){
        sessionObjectHolder.addClick();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "product_page/products";
    }
    @MeasureMethod
    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/product";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/product";
    }
    @PostMapping
    ResponseEntity<Void> addProduct(ProductDTO productDTO){
        productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @MessageMapping("/products")
    public void messageAddProduct(ProductDTO dto) {
        productService.addProduct(dto);
    }


    @GetMapping("/{id}/bucket/remove")
    public String removeFromBucket(@PathVariable Long id, Principal principal){
        if (principal == null){
            return "redirect:/bucket";
        }
        productService.removeFromBucket(id, principal.getName());
        return "redirect:/bucket";
    }
}
