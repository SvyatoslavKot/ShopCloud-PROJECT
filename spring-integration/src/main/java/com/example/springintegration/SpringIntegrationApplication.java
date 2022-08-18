package com.example.springintegration;


import com.example.springintegration.domain.Product;
import com.example.springintegration.integration.ChannelGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;

@SpringBootApplication
@IntegrationComponentScan
public class SpringIntegrationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntegrationApplication.class, args);
/*
        Message<String> message = MessageBuilder
                .withPayload(" It is a Body")
                .setHeader("StringHeader", "Value")
                .setHeader("IntHeader", 1)
                .setHeader("ListOfStrings", Arrays.asList("String1", "String2"))
                .build();

        DirectChannel channel = context.getBean(DirectChannel.class);
        channel.send(message);
*/
        DirectChannel invokeCallGetProducts = context.getBean("invokeCallGetProducts", DirectChannel.class);
        invokeCallGetProducts.send(MessageBuilder.withPayload("").build());

        PollableChannel productsChannel = context.getBean("get_products_channel", PollableChannel.class);
        Message<?> receive = productsChannel.receive();
        System.out.println(receive);
        System.out.println(receive.getPayload());

    }

}
