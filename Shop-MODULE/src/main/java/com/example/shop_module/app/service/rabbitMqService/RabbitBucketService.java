package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.BucketDTO;
import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import com.example.shop_module.app.mq.rabbitMq.abstraction.RabbitProducing;
import com.example.shop_module.app.service.abstraction.BucketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class RabbitBucketService extends RabbitAbstractService implements BucketService {

    private RabbitProducing producer;

    public RabbitBucketService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
        this.producer = getProducer();
    }

    @Override
    public ResponseEntity getBucketByUser(String email) {

        log.info("Produce to Product Module ->Get bucket by User(mail), email: {}", email);
        BucketDTO response = (BucketDTO) producer.convertSendAndReceive(
                RabbitMqSetting.EXCHANGE_PRODUCT_GET_BUCKET.getValue(),
                RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_BUCKET.getValue(),
                email,
                BucketDTO.class);
        log.info("Produce to Product Module return -> response:{}", response);

        if (response == null) {
            response = new BucketDTO();
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity commitBucketToOrder(String email) {
        log.info("Produce to Product Module -> Commit bucket to order, email: {}", email);
        OrderDTO orderDTO = (OrderDTO) producer.convertSendAndReceive(RabbitMqSetting.EXCHANGE_PRODUCT_CONFIRM_ORDER.getValue(),
                RabbitMqSetting.ROUTING_KEY_PRODUCT_CONFIRM_ORDER.getValue(),
                email,
                OrderDTO.class);
        log.info("Produce to Product Module return -> orderDTO: {}", orderDTO);

        if (orderDTO != null) {
            if (orderDTO.getOrderDetails().size() > 0 ) {
                log.info("OrderDto ->  {}", orderDTO);
                return new ResponseEntity(orderDTO, HttpStatus.OK);
            }
            log.error("Корзина пуста или товар закончился на складе", ResponseMessageException.class);
            return new ResponseEntity("Корзина пуста или товар закончился на складе.", HttpStatus.NO_CONTENT);
        }
        log.error("Order Module is not responding", NoConnectedToMQException.class);
        return new ResponseEntity( "Нет ответа попробуте позже.",HttpStatus.SERVICE_UNAVAILABLE);

    }
}
