package com.example.shop_module.mq;



import com.example.shop_module.domain.Order;
import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.mapper.OrderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.shop}")
    private String exchange;

    @Value("${rabbitmq.routingkey-shop}")
    private String routingKey;

    @Value("${rabbitmq.queue-shop}")
    private String queue;

    @Value("${rabbitmq.exchange.productItem}")
    private String exchangePr;

    @Value("${rabbitmq.routingkey.productItem}")
    private String routingKeyPr;

    @Value("${rabbitmq.queue.productItem}")
    private String queuePr;

    @Autowired
    private OrderMapper orderMapper;

    //--Topic queue
    @Bean
    public Queue queue() {
        return new Queue(queue, false);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
    }

    public void sendOrder(Order order){
        rabbitTemplate.setExchange(exchange);
        log.info("order id -> " + order.getId());
        rabbitTemplate.convertAndSend(exchange,routingKey,orderMapper.toDto(order));
    }
    @Bean
    public Queue queuePr() {
        return new Queue(queuePr, false);
    }
    @Bean
    public TopicExchange topicExchangePr(){
        return new TopicExchange(exchangePr);
    }

    @Bean
    public Binding bindingPr(Queue queuePr, TopicExchange topicExchangePr){
        return BindingBuilder.bind(queuePr).to(topicExchangePr).with(routingKeyPr);
    }
    public void sendmsg (String msg){
        log.info("Produce - " + msg);
        rabbitTemplate.setExchange(exchangePr);
        rabbitTemplate.convertAndSend(exchangePr,routingKeyPr,msg);
    }

    public ProductDTO getProductItem(Long id){
        rabbitTemplate.setExchange(exchangePr);
        log.info("Producer Product id -> " + id);
       // Message  response = (Message) rabbitTemplate.convertSendAndReceive(routingKeyPr,String.valueOf(id));

        ProductDTO dto =  rabbitTemplate.convertSendAndReceiveAsType(routingKeyPr,String.valueOf(id), ParameterizedTypeReference.forType(ProductDTO.class));
        //System.out.println(personJsonObject);
       // OrderMapper resp = new ObjectMapper();
        log.info("return -> " + dto);
        return dto;
    }


    /*
    static int i =1;
    @Scheduled(fixedDelay = 3000)
    public void process(){
        i++;
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.convertAndSend(exchange,routingKey, "counter -> " + i);
        System.out.println("counter -> " + i);
    }

     */
}
