package com.example.shop_module.mq;

import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@AllArgsConstructor
public class ProduceOrderModule {

    private final RabbitSettings settings;
    private final RabbitTemplate rabbitTemplate;

    @Bean
    public Queue queueOrderCreate() {
        return new Queue(settings.getQueueOrderCreate(), false);
    }
    @Bean
    public TopicExchange topicExchangeOrderCreate(){
        return new TopicExchange(settings.getExchangeOrderCreate());
    }
    @Bean
    public Binding bindingOrderCreate(Queue queueOrderCreate, TopicExchange topicExchangeOrderCreate){
        return BindingBuilder.bind(queueOrderCreate).to(topicExchangeOrderCreate).with(settings.getRoutingKeyOrderCreate());
    }

    public Long createOrder(OrderDTO orderDTO){
        rabbitTemplate.setExchange(settings.getExchangeOrderCreate());
        Map<String, Object> response = rabbitTemplate.convertSendAndReceiveAsType(settings.getRoutingKeyOrderCreate(),orderDTO, ParameterizedTypeReference.forType(Map.class));
        if (response != null){
            if (response.get("exception") == null){
                if (response.get("orderId") != null){
                    Integer orderId = (Integer) response.get("orderId");
                    if ( orderId != null && orderId > 0){
                        Long id = Long.valueOf(orderId);
                        return id;
                    }
                    throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось сформировать заказ.");
                }
                throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось получить информацию от заказе.");
            }
            String msg = (String) response.get("exception");
            System.out.println("response exception - >" + msg);
            throw new ResponseMessageException(HttpStatus.CONFLICT,msg);
        }
        throw new NoConnectedToMQException();
    }



    @Bean
    public Queue queueOrderGet() {
        return new Queue(settings.getQueueOrderGet(), false);
    }
    @Bean
    public TopicExchange topicExchangeOrderGet(){
        return new TopicExchange(settings.getExchangeOrderGet());
    }
    @Bean
    public Binding bindingOrderGet(Queue queueOrderGet, TopicExchange topicExchangeOrderGet){
        return BindingBuilder.bind(queueOrderGet).to(topicExchangeOrderGet).with(settings.getRoutingKeyOrderGet());
    }

    public OrderDTO getOrderById (Long id) {
        rabbitTemplate.setExchange(settings.getExchangeOrderGet());
        OrderDTO response = rabbitTemplate.convertSendAndReceiveAsType(settings.getRoutingKeyOrderGet(),id , ParameterizedTypeReference.forType(OrderDTO.class));

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
                throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось получить информацию от заказе с id " + id);
           }
        throw new NoConnectedToMQException();

    }


    @Bean
    public Queue queueOrderUpdate() {
        return new Queue(settings.getQueueOrderUpdate(), false);
    }
    @Bean
    public TopicExchange topicExchangeOrderUpdate(){
        return new TopicExchange(settings.getExchangeOrderUpdate());
    }
    @Bean
    public Binding bindingOrderUpdate(Queue queueOrderUpdate, TopicExchange topicExchangeOrderUpdate){
        return BindingBuilder.bind(queueOrderUpdate).to(topicExchangeOrderUpdate).with(settings.getRoutingKeyOrderUpdate());
    }

    public boolean orderUpdate (OrderDTO dto) {
        rabbitTemplate.setExchange(settings.getExchangeOrderUpdate());
        Map<String , Object> responseMap =  rabbitTemplate.convertSendAndReceiveAsType(settings.getRoutingKeyOrderUpdate(),dto , ParameterizedTypeReference.forType(Map.class));

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
                throw new ResponseMessageException(HttpStatus.NO_CONTENT, msg);
            }
            throw new ResponseMessageException(HttpStatus.NO_CONTENT,"Не удалось обновить данные в заказе с id " + dto.getId());
        }
        throw new NoConnectedToMQException();
    }

    public void cancelOrder(Long id) {
        //event
    }
}
