package com.example.shop_module.app.mq.rabbitMq;

import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.dto.BucketDTO;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceProductModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitProduceProductModule implements ProduceProductModule {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public ProductDTO getProductItem(Long id) {
        log.info("Produce to Product Module -> product item by id, Id: {}", id);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_PRODUCT_GET_BY_ID.getValue());
        ProductDTO dto =  rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_BY_ID.getValue(),String.valueOf(id), ParameterizedTypeReference.forType(ProductDTO.class));
        log.info("Produce to Product Module  return -> dto: {}", dto);
            if (dto != null){
                if (dto.getId() != null) {
                    return dto;
                }
                log.error("Продукт с id -> " + id + " не найден.", ResponseMessageException.class);
                throw new ResponseMessageException(HttpStatus.NOT_FOUND, "Продукт с id -> " + id + " не найден.");
            }
        log.error("Order Module is not responding", NoConnectedToMQException.class);
            throw  new NoConnectedToMQException();
    }

    @Override
    public void addToBucketByID(Long productId, String mail) {
        log.info("Produce to Product Module -> Add to bucket by id, productId: {}, mail: {}", productId,mail);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_PRODUCT_ADD_BUCKET.getValue());
        Map<String , Object> sendMsg = new HashMap<>();
        sendMsg.put("id", productId);
        sendMsg.put("mail", mail);
        rabbitTemplate.convertAndSend(RabbitMqSetting.ROUTING_PRODUCT_ADD_BUCKET.getValue(),sendMsg);
    }

    @Override
    public BucketDTO getBucketByUser(String email) {
        log.info("Produce to Product Module ->Get bucket by User(mail), email: {}", email);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_PRODUCT_GET_BUCKET.getValue());
        BucketDTO response = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_BUCKET.getValue(), email, ParameterizedTypeReference.forType(BucketDTO.class));
        log.info("Produce to Product Module return -> response:{}", response);

        return  response;
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        log.info("Produce to Product Module -> remove fom Bucket, productId: {}, mail: {}", productId, mail);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_PRODUCT_REMOVE_FROM_BUCKET.getValue());
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("productId" , productId);
        msgMap.put("mail" , mail);
        rabbitTemplate.convertAndSend(RabbitMqSetting.ROUTING_KEY_PRODUCT_REMOVE_FROM_BUCKET.getValue(), msgMap);
    }

    @Override
    public List<ProductDTO> getAll() {
        log.info("Produce to Product Module -> Get All");
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_PRODUCT_GET_ALL.getValue());
        String msg = "msg";
        List<ProductDTO> responseList = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_ALL.getValue(),msg, ParameterizedTypeReference.forType(List.class));
        log.info("Produce to Product Module return -> responseList: {}", responseList);
        if (responseList != null){
            return responseList;
        }
        log.error("Order Module is not responding", NoConnectedToMQException.class);
        throw new NoConnectedToMQException();
    }

    @Override
    public void addProduct(ProductDTO dto){
        log.info("Produce to Product Module -> Add Product, ProductDto: {}", dto);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_PRODUCT_ADD.getValue());
        rabbitTemplate.convertAndSend( dto);
    }

    @Override
    public OrderDTO commitBucketToOrder(String email) throws NoConnectedToMQException, ResponseMessageException {
        log.info("Produce to Product Module -> Commit bucket to order, email: {}", email);
        rabbitTemplate.setExchange(RabbitMqSetting.EXCHANGE_PRODUCT_CONFIRM_ORDER.getValue());
        OrderDTO orderDTO = rabbitTemplate.convertSendAndReceiveAsType(RabbitMqSetting.ROUTING_KEY_PRODUCT_CONFIRM_ORDER.getValue(), email, ParameterizedTypeReference.forType(OrderDTO.class));
        log.info("Produce to Product Module return -> orderDTO: {}", orderDTO);
        if (orderDTO != null) {
            if (orderDTO.getOrderDetails().size() > 0 ) {
                System.out.println("OrderDto -> " + orderDTO);
                return orderDTO;
            }
            log.error("Корзина пуста или товар закончился на складе", ResponseMessageException.class);
            throw new ResponseMessageException(HttpStatus.OK, "Корзина пуста или товар закончился на складе");
        }
        log.error("Order Module is not responding", NoConnectedToMQException.class);
        throw new NoConnectedToMQException();
    }

    @Override
    public void updateProduct(ProductDTO updateProduct0) {

    }
}
