package com.example.shopclient_module.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.shopclient_module.app.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        Contact contact = new Contact("Svyatoslav Kotov",
                "https://github.com/SvyatoslavKot/ShopCloud-PROJECT", "kotov.svyat@mail.ru");
        return new ApiInfoBuilder()
                .title("ShopCloud-PROJECT API")
                .description("Api for example project")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("jjj")
                .contact(contact)
                .build();
    }



}
