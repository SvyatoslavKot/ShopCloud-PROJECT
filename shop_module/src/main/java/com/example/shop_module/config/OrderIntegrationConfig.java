package com.example.shop_module.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;

@Configuration
@ImportResource("/integration/order-integration.xml")
public class OrderIntegrationConfig {

    private DirectChannel ordersChannel;

    public OrderIntegrationConfig(@Qualifier(value = "ordersChannel") DirectChannel ordersChannel) {
        this.ordersChannel = ordersChannel;
    }

    public DirectChannel getOrdersChannel() {
        return ordersChannel;
    }
}
