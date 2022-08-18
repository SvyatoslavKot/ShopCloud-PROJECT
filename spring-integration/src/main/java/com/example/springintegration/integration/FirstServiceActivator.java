package com.example.springintegration.integration;

import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FirstServiceActivator {
    @ServiceActivator(inputChannel = "channel")
    public void listenerChannel(@Payload String payload, @Headers Map<String, Object> headers) {
        System.out.println("******************");
        headers.forEach((k,v) ->System.out.println(k + " -> " + v));
        System.out.println("Payload -> " + payload);
        System.out.println("******************");
    }
        public MessageHandler productsOut() {
            HttpRequestExecutingMessageHandler handler =
                    new HttpRequestExecutingMessageHandler("http://localhost:8080/products");
            handler.setCharset("UTF-8");
            handler.setHttpMethod(HttpMethod.GET);
            return handler;
    }

}
