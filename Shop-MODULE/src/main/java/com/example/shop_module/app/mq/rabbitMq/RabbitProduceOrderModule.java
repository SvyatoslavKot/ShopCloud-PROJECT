package com.example.shop_module.app.mq.rabbitMq;


import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceOrderModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitProduceOrderModule implements ProduceOrderModule {

    private final RabbitTemplate rabbitTemplate;
    private final String encoding = StandardCharsets.UTF_8.name();

    @Override
    public Long createOrder(OrderDTO orderDTO){
        log.info("Produce to OrderModule -> createOrder, orderDTO: {}", orderDTO);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_ORDER_CREATE.getValue());
        Map<String, Object> response = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_ORDER_CREATE.getValue(),orderDTO, ParameterizedTypeReference.forType(Map.class));
        log.info("Produce to OrderModule -> createOrder return -> response: {}", response);
        if (response != null){
            if (response.get("exception") == null){
                if (response.get("orderId") != null){
                    Integer orderId = (Integer) response.get("orderId");
                    if ( orderId != null && orderId > 0){
                        Long id = Long.valueOf(orderId);
                        return id;
                    }
                    log.error("Не удалось сформировать заказ.", ResponseMessageException.class);
                    throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось сформировать заказ.");
                }
                log.error("Не удалось получить информацию от заказе.", ResponseMessageException.class);
                throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось получить информацию от заказе.");
            }
            String msg = (String) response.get("exception");
            log.error(msg, ResponseMessageException.class);
            throw new ResponseMessageException(HttpStatus.CONFLICT,msg);
        }
        log.error("Order Module is not responding", NoConnectedToMQException.class);
        throw new NoConnectedToMQException();
    }

    @Override
    public OrderDTO getOrderById (Long id) {
        log.info("Produce to OrderModule -> get Order By Id, id: {}", id);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_ORDER_GET.getValue());
        OrderDTO response = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_ORDER_GET.getValue(),id , ParameterizedTypeReference.forType(OrderDTO.class));
        log.info("Produce to OrderModule -> get Order By Id return -> response: {}", response);
        if (response != null) {
            if (response.getId() != null) {
                try{
                    System.out.println("DTO -> " + response);
                    return response;
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage() + " " + e.getLocalizedMessage());
                }
            }
            log.error("Не удалось получить информацию от заказе с id: " + id, ResponseMessageException.class);
            throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось получить информацию от заказе с id " + id);
        }
        log.error("Order Module is not responding", NoConnectedToMQException.class);
        throw new NoConnectedToMQException();

    }

    @Override
    public boolean orderUpdate (OrderDTO dto) {
        log.info("Produce to OrderModule -> Order update, orderDTO: {}", dto);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_ORDER_UPDATE.getValue());
        Map<String , Object> responseMap =  rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_ORDER_UPDATE.getValue(),dto , ParameterizedTypeReference.forType(Map.class));
        log.info("Produce to OrderModule -> Order update return -> response: {}", responseMap);
        if (responseMap != null) {
            if (responseMap.get("OK") != null) {
                try{
                    System.out.println("response -> " + responseMap.get("OK"));
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage() + " " + e.getLocalizedMessage());
                }
            }
            if (responseMap.get("exception") != null) {
                String msg = (String) responseMap.get("exception");
                log.error(msg, ResponseMessageException.class);
                throw new ResponseMessageException(HttpStatus.NO_CONTENT, msg);
            }
            log.error("Не удалось обновить данные в заказе с id " + dto.getId(), ResponseMessageException.class);
            throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось обновить данные в заказе с id " + dto.getId());
        }
        log.error("Order Module is not responding", NoConnectedToMQException.class);
        throw new NoConnectedToMQException();
    }


    @Override
    public void cancelOrder(Long id) {
        //event
    }
}
