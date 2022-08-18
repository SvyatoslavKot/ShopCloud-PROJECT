package com.example.springintegration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("integration/http-product-gateway.xml")
public class ProductIntegrationConfig {
}
