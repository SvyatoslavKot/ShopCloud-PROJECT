package com.example.shop_module.service.soap;

import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.service.ProductService;

import com.example.shop_module.xsd.product.GetProductRequest;
import com.example.shop_module.xsd.product.GetProductResponse;
import com.example.shop_module.xsd.product.ProductsWs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

public class ProductEndpoint {
    public static final String NAMESPACE_URL = "http://example.com/shop_module/ws/product";

    private ProductService productService;

    @Autowired
    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProductWS(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        productService.getAll()
                .forEach(productDTO -> response.getProducts().add(createProductWs(productDTO)));
        return response;
    }
    private ProductsWs createProductWs(ProductDTO productDTO){
        ProductsWs ws = new ProductsWs();
        ws.setId(productDTO.getId());
        ws.setPrice(Double.parseDouble(productDTO.getPrice().toString()));
        ws.setTitle(productDTO.getTitle());
        return ws;
    }






}
