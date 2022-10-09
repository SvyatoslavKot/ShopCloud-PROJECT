package com.example.shop_module.mq;


import com.example.shop_module.dto.BucketDTO;
import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class ProduceProductModule {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitSettings settings;


    @Bean
    public Queue queueProductGetById() {
        return new Queue(settings.getQueueProductGetById(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductGetById(){
        return new TopicExchange(settings.getExchangeProductGetById());
    }
    @Bean
    public Binding bindingProductGetById(Queue queueProductGetById, TopicExchange topicExchangeProductGetById){
        return BindingBuilder.bind(queueProductGetById).to(topicExchangeProductGetById).with(settings.getRoutingKeyProductGetById());
    }

    public ProductDTO getProductItem(Long id) {
        rabbitTemplate.setExchange(settings.getExchangeProductGetById());
        ProductDTO dto =  rabbitTemplate.convertSendAndReceiveAsType(settings.getRoutingKeyProductGetById(),String.valueOf(id), ParameterizedTypeReference.forType(ProductDTO.class));
        if (dto != null){
            if (dto.getId() != null) {
                return dto;
            }
            throw new ResponseMessageException(HttpStatus.NOT_FOUND, "Продукт с id -> " + id + " не найден.");
        }
        throw  new NoConnectedToMQException();
    }


    @Bean
    public Queue queueProductAddBucket() {
        return new Queue(settings.getQueueProductAddBucket(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductAddBucket(){
        return new TopicExchange(settings.getExchangeProductAddBucket());
    }
    @Bean
    public Binding bindingProductAddBucket(Queue queueProductAddBucket, TopicExchange topicExchangeProductAddBucket){
        return BindingBuilder.bind(queueProductAddBucket).to(topicExchangeProductAddBucket).with(settings.getRoutingKeyProductAddBucket());
    }

    public void addToBucketByID(Long productId, String mail) {
        rabbitTemplate.setExchange(settings.getExchangeProductAddBucket());
        Map<String , Object> sendMsg = new HashMap<>();
        sendMsg.put("id", productId);
        sendMsg.put("mail", mail);
        rabbitTemplate.convertAndSend(settings.getRoutingKeyProductAddBucket(),sendMsg);
    }


    @Bean
    public Queue queueProductGetBucket() {
        return new Queue(settings.getQueueProductGetBucket(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductGetBucket(){
        return new TopicExchange(settings.getExchangeProductGetBucket());
    }
    @Bean
    public Binding bindingProductGetBucket(Queue queueProductGetBucket, TopicExchange topicExchangeProductGetBucket){
        return BindingBuilder.bind(queueProductGetBucket).to(topicExchangeProductGetBucket).with(settings.getRoutingKeyProductGetBucket());
    }

    public BucketDTO getBucketByUser(String email) {
        rabbitTemplate.setExchange(settings.getExchangeProductGetBucket());
        BucketDTO response = rabbitTemplate.convertSendAndReceiveAsType(settings.getRoutingKeyProductGetBucket(), email, ParameterizedTypeReference.forType(BucketDTO.class));
        return  response;
    }


    @Bean
    public Queue queueProductRemoveFromBucket() {
        return new Queue(settings.getQueueProductRemoveFromBucket(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductRemoveFromBucket(){
        return new TopicExchange(settings.getExchangeProductRemoveFromBucket());
    }
    @Bean
    public Binding bindingProductRemoveFromBucket(Queue queueProductRemoveFromBucket, TopicExchange topicExchangeProductRemoveFromBucket){
        return BindingBuilder.bind(queueProductRemoveFromBucket).to(topicExchangeProductRemoveFromBucket).with(settings.getRoutingKeyProductRemoveFromBucket());
    }
    public void removeFromBucket(Long productId, String mail) {
        rabbitTemplate.setExchange(settings.getExchangeProductRemoveFromBucket());
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("productId" , productId);
        msgMap.put("mail" , mail);
        rabbitTemplate.convertAndSend(settings.getRoutingKeyProductRemoveFromBucket(), msgMap);
    }


    @Bean
    public Queue queueProductConfirmOrder() {
        return new Queue(settings.getQueueProductConfirmOrder(), false);
    }
    @Bean
    public TopicExchange topicExchangeProductConfirmOrder(){
        return new TopicExchange(settings.getExchangeProductConfirmOrder());
    }
    @Bean
    public Binding bindingProductConfirmOrder(Queue queueProductConfirmOrder, TopicExchange topicExchangeProductConfirmOrder){
        return BindingBuilder.bind(queueProductConfirmOrder).to(topicExchangeProductConfirmOrder).with(settings.getRoutingKeyProductConfirmOrder());
    }
    public OrderDTO commitBucketToOrder(String email) throws NoConnectedToMQException, ResponseMessageException {
        rabbitTemplate.setExchange(settings.getExchangeProductConfirmOrder());
        OrderDTO orderDTO = rabbitTemplate.convertSendAndReceiveAsType(settings.getRoutingKeyProductConfirmOrder(), email, ParameterizedTypeReference.forType(OrderDTO.class));
        if (orderDTO != null) {
            if (orderDTO.getOrderDetails().size() > 0 ) {
                System.out.println("OrderDto -> " + orderDTO);
                return orderDTO;
            }
            throw new ResponseMessageException(HttpStatus.OK, "Корзина пуста или товар закончился на складе");
        }
        throw new NoConnectedToMQException();
    }
}
