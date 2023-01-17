package com.example.shop_module.app.dto;

import org.apache.catalina.LifecycleState;

import java.util.List;

public class ProductList {
    private List<ProductDTO> productDTOS;

    public void setProductDTOS(List<ProductDTO> productDTOS) {
        this.productDTOS = productDTOS;
    }
}
