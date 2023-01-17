package com.example.shop_module.app.config;


import com.example.grpc.ClientServiceGrpc;
import com.example.grpc.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class GrpcConfig {

    @Value("${product-module.grpc.host}")
    private String productHost;
    @Value("${grps.server.port}")
    private int grpcServerPort;
    @Value("${product-module.grpc.port}")
    private String productPort;
    @Value("${client-shop-module.grpc.host}")
    private String clientHost;
    @Value("${client-shop-module.grpc.port}")
    private String clientPort;



    @Bean
    ProductServiceGrpc.ProductServiceBlockingStub  productServiceBlockingStub() {
        try{
            ManagedChannel channel = ManagedChannelBuilder.forTarget(productHost+":"+productPort)
                    .usePlaintext().build();
            return ProductServiceGrpc.newBlockingStub(channel);
        }catch (Exception e) {
           log.error("GrpcProductService don't create, msg -> {}", e.getMessage());
        }
        return null;
    }

    @Bean
    ClientServiceGrpc.ClientServiceBlockingStub clientServiceBlockingStub() {
        try{
            ManagedChannel channel = ManagedChannelBuilder.forTarget(clientHost+":"+clientPort)
                    .usePlaintext().build();
            return ClientServiceGrpc.newBlockingStub(channel);
        }catch (Exception e) {
            log.error("GrpcClientService don't create, msg -> {}", e.getMessage());
        }
        return null;
    }

    @Bean
    public void server() throws IOException {
        Server server  = ServerBuilder.forPort(grpcServerPort).build();
        server.start();
        System.out.println("Server started.");
    }
}
