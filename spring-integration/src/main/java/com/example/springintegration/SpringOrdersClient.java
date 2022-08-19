package com.example.springintegration;

import com.example.springintegration.config.OrderIntegrationConfig;
import com.example.springintegration.domain.Order;
import com.example.springintegration.domain.OrderDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class SpringOrdersClient {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringOrdersClient.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
        ConfigurableApplicationContext context = app.run(args);

        OrderIntegrationConfig config = context.getBean("orderIntegrationConfig", OrderIntegrationConfig.class);

        DirectChannel ordersChannel = config.getOrdersChannel();

        Order order = new Order();
        order.setOrderId(1234l);
        order.setUsername("user");
        order.setAddress("Address");

        List<OrderDetails> list = new ArrayList<>();
        list.add( new OrderDetails("milk", 10.0, 2.0, 20.0));
        list.add(new OrderDetails("teat", 50.0, 2.0, 100.0));

        order.setDetails(list);

        Message<Order> message = MessageBuilder
                .withPayload(order)
                .setHeader("Content-Type", "application/json")
                .build();
        ordersChannel.send(message);

    }

}
