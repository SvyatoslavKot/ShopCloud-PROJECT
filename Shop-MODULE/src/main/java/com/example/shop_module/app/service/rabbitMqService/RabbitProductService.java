package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import com.example.shop_module.app.mq.rabbitMq.abstraction.RabbitProducing;
import com.example.shop_module.app.service.abstraction.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class RabbitProductService extends RabbitAbstractService implements ProductService {

    private RabbitProducing producer;
    private final SimpMessagingTemplate templateMsg;

    public RabbitProductService(RabbitTemplate rabbitTemplate, SimpMessagingTemplate templateMsg) {
        super(rabbitTemplate);
        this.templateMsg = templateMsg;
        this.producer = getProducer();
    }

    @Override
    public ResponseEntity getAll() {
        try{
            List<ProductDTO> productList = (List<ProductDTO>) producer.convertSendAndReceive(RabbitMqSetting.EXCHANGE_PRODUCT_GET_ALL.getValue(),
                    RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_ALL.getValue(),
                    "msg",
                    List.class);
            return new ResponseEntity(productList, HttpStatus.OK);
        }catch (NoConnectedToMQException nce) {
            return new ResponseEntity(nce.getMessage(), nce.getStatus());
        }

    }

    @Override
    public ResponseEntity getById(Long id) throws ResponseMessageException {
        try {
            ProductDTO dto = (ProductDTO) producer.convertSendAndReceive(
                    RabbitMqSetting.EXCHANGE_PRODUCT_GET_BY_ID.getValue(),
                    RabbitMqSetting.ROUTING_KEY_PRODUCT_GET_BY_ID.getValue(),
                    String.valueOf(id),
                    ProductDTO.class
                    );
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (NoConnectedToMQException e){
            return new ResponseEntity(e.getMessage(),e.getStatus());
        }catch (ResponseMessageException rme) {
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }
    }



    @Override
    public void addToUserBucket(Long productId, String mail) {
        Map<String , Object> sendMsg = new HashMap<>();
        sendMsg.put("id", productId);
        sendMsg.put("mail", mail);
        producer.produce(
                RabbitMqSetting.EXCHANGE_PRODUCT_ADD_BUCKET.getValue(),
                RabbitMqSetting.ROUTING_PRODUCT_ADD_BUCKET.getValue(),
                sendMsg
                );
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("productId" , productId);
        msgMap.put("mail" , mail);
        producer.produce(
                RabbitMqSetting.EXCHANGE_PRODUCT_REMOVE_FROM_BUCKET.getValue(),
                RabbitMqSetting.ROUTING_KEY_PRODUCT_REMOVE_FROM_BUCKET.getValue(),
                msgMap);
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        templateMsg.convertAndSend("/topic/products", productDTO);
        producer.produce(RabbitMqSetting.EXCHANGE_PRODUCT_ADD.getValue(),
                RabbitMqSetting.ROUTING_KEY_PRODUCT_ADD.getValue(),
                productDTO);
    }

    @Override
    public ResponseEntity getByParam(Optional<Integer> page, Optional<Integer> size, Optional<String> title, Optional<BigDecimal> min, Optional<BigDecimal> max) {
        return null;
    }

    @Override
    public void updateProduct(ProductDTO updateProduct) {

    }

    @Override
    public void addProductFromFile(String name, MultipartFile file) {

    }
}
