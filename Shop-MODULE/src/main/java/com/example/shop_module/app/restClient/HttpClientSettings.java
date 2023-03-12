package com.example.shop_module.app.restClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpClientSettings {

    @Value("${product-module.host}")
    private String PRODUCT_HOST;
    @Value("${product-module.port}")
    private String PRODUCT_PORT;
    @Value("${client-shop-module.port}")
    private String CLIENT_PORT;
    @Value("${api-version}")
    private String API_VERSION;

    public String getPRODUCT_HOST() {
        return PRODUCT_HOST;
    }

    public String getPRODUCT_PORT() {
        return PRODUCT_PORT;
    }

    public String getAPI_VERSION() {
        return API_VERSION;
    }

    public String getCLIENT_PORT() {
        return CLIENT_PORT;
    }
}
