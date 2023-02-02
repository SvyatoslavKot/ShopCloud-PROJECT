package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceProductModule;
import com.example.shop_module.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class RabbitProductService implements ProductService {

    private final SimpMessagingTemplate templateMsg;
    private final ProduceProductModule produceProductModule;

    public RabbitProductService(SimpMessagingTemplate templateMsg,
                                @Qualifier("rabbitProduceProductModule") ProduceProductModule produceProductModule) {
        this.templateMsg = templateMsg;
        this.produceProductModule = produceProductModule;
    }

    @Override
    public ResponseEntity getAll() {
        try{
            var productList = produceProductModule.getAll();
            return new ResponseEntity(productList, HttpStatus.OK);
        }catch (NoConnectedToMQException nce) {
            log.error(nce.getMessage(), nce.getCause());
            return new ResponseEntity(nce.getMessage(), nce.getStatus());
        }
    }

    @Override
    public ResponseEntity getById(Long id) throws ResponseMessageException {
        try {
            ProductDTO dto = produceProductModule.getProductItem(id);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (NoConnectedToMQException e){
            return new ResponseEntity(e.getMessage(),e.getStatus());
        }catch (ResponseMessageException rme) {
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }
    }

    @Override
    public void addToUserBucket(Long productId, String mail) {
        produceProductModule.addToBucketByID( productId, mail);
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        produceProductModule.removeFromBucket(productId, mail);
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO) {
        produceProductModule.addProduct(productDTO);
        templateMsg.convertAndSend("/topic/products", productDTO);
    }

    @Override
    public ResponseEntity getByParam(Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<String> title,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max) {
        //todo create method
        return null;
    }

    @Override
    public void updateProduct(ProductDTO updateProduct) {

    }
}
