package com.example.productmodule.app.config;

import com.example.productmodule.app.gRPC.ProductServiceGRPC;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GRpcServerConfig {

   /*  @Bean
    public void server() throws IOException {
       Server server  = ServerBuilder.forPort(9091)//.addService(null//new ProductServiceGRPC())
        .build();
        server.start();

        System.out.println("Server started.");
    }

        */
}
