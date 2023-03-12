package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import com.example.shop_module.app.mq.rabbitMq.abstraction.RabbitProducing;
import com.example.shop_module.app.service.abstraction.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public class RabbitOrderService extends RabbitAbstractService implements OrderService {

    private RabbitProducing producer;

    public RabbitOrderService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
        this.producer = getProducer();
    }

    @Override
    public ResponseEntity getOrderByMail(String mail) {
        return null;
    }

    @Override
    public ResponseEntity createOrder(OrderDTO orderDTO) {
        log.info("Produce to OrderModule -> createOrder, orderDTO: {}", orderDTO);
        Map<Object, Object> response =  producer.convertSendAndReceiveMap(RabbitMqSetting.EXCHANGE_ORDER_CREATE.getValue(),
                RabbitMqSetting.ROUTING_KEY_ORDER_CREATE.getValue(),
                orderDTO);
        log.info("Produce to OrderModule -> createOrder return -> response: {}", response);

        if (response != null){
            if (response.get("exception") == null){
                if (response.get("orderId") != null){
                    Integer orderId = (Integer) response.get("orderId");
                    if ( orderId != null && orderId > 0){
                        Long id = Long.valueOf(orderId);
                        return new ResponseEntity(id, HttpStatus.OK);
                    }
                    log.info("Не удалось сформировать заказ. OrderId -> {}", orderId);
                    return new ResponseEntity("Не удалось сформировать заказ.", HttpStatus.NO_CONTENT);
                }
                log.info("Не удалось получить информацию о заказе. OrderId -> {}", response.get("orderId"));
                return new ResponseEntity("Не удалось получить информацию о заказе.", HttpStatus.NO_CONTENT);
            }
            String msg = (String) response.get("exception");
            log.info("msg");
            return new ResponseEntity(msg, HttpStatus.CONFLICT);
        }
        log.info("Order Module is not responding");
        return new ResponseEntity("Order Module is not responding", HttpStatus.SERVICE_UNAVAILABLE);

    }

    @Override
    public ResponseEntity getOrderById(Long id) {

        log.info("Produce to OrderModule -> get Order By Id, id: {}", id);

        OrderDTO response = (OrderDTO) producer.convertSendAndReceive(RabbitMqSetting.EXCHANGE_ORDER_GET.getValue(),
                RabbitMqSetting.ROUTING_KEY_ORDER_GET.getValue(),
                id,
                OrderDTO.class);
        log.info("Produce to OrderModule -> get Order By Id return -> response: {}", response);

        if (response != null) {
            if (response.getId() != null) {
                try{
                    log.info("DTO ->  {}", response);
                    return new ResponseEntity(response, HttpStatus.OK);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage(), e.getCause());
                    return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
                }
            }
            log.info("Не удалось получить информацию от заказе с id ->  {}", id);
            return new ResponseEntity("Не удалось получить информацию от заказе с id " + id, HttpStatus.NO_CONTENT);
        }
        log.info("Order Module is not responding");
        return new ResponseEntity("Order Module is not responding", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity updateOrder(OrderDTO orderDTO) {

        log.info("Produce to OrderModule -> Order update, orderDTO: {}", orderDTO);
        Map<Object, Object> responseMap =  producer.convertSendAndReceiveMap(RabbitMqSetting.EXCHANGE_ORDER_UPDATE.getValue(),
                RabbitMqSetting.ROUTING_KEY_ORDER_UPDATE.getValue(),
                orderDTO );
        log.info("Produce to OrderModule -> Order update return -> response: {}", responseMap);

        if (responseMap != null) {
            if (responseMap.get("OK") != null) {
                try{
                    log.info("response ->  {}", responseMap.get("OK"));
                    return new ResponseEntity(orderDTO,HttpStatus.OK);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage(), e.getCause());
                    return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
                }
            }
            if (responseMap.get("exception") != null) {
                log.info("response exception  ->  {}", responseMap.get("exception"));
                String msg = (String) responseMap.get("exception");
                return new ResponseEntity(msg, HttpStatus.NO_CONTENT);
            }
            log.info("Не удалось обновить данные в заказе с id ->  {}", orderDTO.getId());
            return new ResponseEntity("Не удалось обновить данные в заказе с id " + orderDTO.getId(), HttpStatus.NO_CONTENT);
        }
        log.info("Order Module is not responding");
        return new ResponseEntity("Order Module is not responding", HttpStatus.SERVICE_UNAVAILABLE);

    }

    @Override
    public void cancelOrder(Long id) {

    }
}
