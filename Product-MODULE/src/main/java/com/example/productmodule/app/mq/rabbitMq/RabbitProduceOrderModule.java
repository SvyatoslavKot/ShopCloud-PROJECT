package com.example.productmodule.app.mq.rabbitMq;

import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.dto.OrderRequest;
import com.example.productmodule.app.mq.ProduceOrderModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RabbitProduceOrderModule implements ProduceOrderModule {

    private final RabbitProducer rabbitProducer;

    public RabbitProduceOrderModule(RabbitProducer rabbitProducer) {
        this.rabbitProducer = rabbitProducer;
    }

    @Override
    public void confirmOrder(List<ProductDTO> products, String mail)  {
        try {
            OrderRequest request = new OrderRequest(products, mail);
            rabbitProducer.produce(RabbitMqSettings.EXCHANGE_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET.getValue(),
                                RabbitMqSettings.ROUTIN_KEY_ORDER_MODULE_CONFIRM_ORDER_FROM_BUCKET.getValue(),
                                request);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
