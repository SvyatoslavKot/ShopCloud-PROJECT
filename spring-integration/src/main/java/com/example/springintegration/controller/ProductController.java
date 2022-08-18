package com.example.springintegration.controller;

import com.example.springintegration.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Value("${server.port:8080}")
    private int port;

    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product("Molk", 33.33));
        products.add(new Product("Chocolate", 93.33));
        products.add(new Product("Better", 99.33));
        products.add(new Product("Bread", 63.33));

    }

    @GetMapping
    public List<Product> getProducts() {
        System.out.println("server port -> " + port);
        return products;
    }
}
