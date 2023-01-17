package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.BucketDTO;
import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceProductModule;
import com.example.shop_module.app.service.BucketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RabbitBucketService implements BucketService {

    private final ProduceProductModule rabbitProduceProduct;

    public RabbitBucketService(@Qualifier("rabbitProduceProductModule") ProduceProductModule rabbitProduceProduct) {
        this.rabbitProduceProduct = rabbitProduceProduct;
    }

    @Override
    public ResponseEntity getBucketByUser(String email) {
        BucketDTO bucketDTO = rabbitProduceProduct.getBucketByUser(email);
        if (bucketDTO == null) {
            bucketDTO = new BucketDTO();
        }
        return new ResponseEntity(bucketDTO,HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity commitBucketToOrder(String email) {
        try {
            OrderDTO order = rabbitProduceProduct.commitBucketToOrder(email);
            return new ResponseEntity(order, HttpStatus.OK);
        }catch (NoConnectedToMQException nce){
            return new ResponseEntity(nce.getMessage(),nce.getStatus());
        }catch (ResponseMessageException rme) {
            return new ResponseEntity("Корзина пуста или товар закончился на складе.", HttpStatus.NO_CONTENT);
        }
    }
}
