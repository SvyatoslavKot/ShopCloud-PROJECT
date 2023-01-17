package com.example.productmodule.app.mq;

import com.example.productmodule.app.dto.ProductDTO;

import java.util.List;

public interface ProduceOrderModule {

    void confirmOrder(List<ProductDTO> products, String mail);
}
