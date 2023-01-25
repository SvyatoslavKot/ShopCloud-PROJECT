package com.example.shop_module.app.config;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class ApplicationConf {

    @Bean
    MultipartConfigElement multipartConfigElement () {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(500l));
        factory.setMaxRequestSize(DataSize.ofKilobytes(500l));
        return factory.createMultipartConfig();
    }
}
