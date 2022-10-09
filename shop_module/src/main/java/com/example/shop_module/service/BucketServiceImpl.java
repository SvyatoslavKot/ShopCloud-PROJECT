package com.example.shop_module.service;

import com.example.shop_module.dto.BucketDTO;
import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import com.example.shop_module.mq.ProduceProductModule;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

    private ProduceProductModule producer;

    @Override
    public BucketDTO getBucketByUser(String email) {
        BucketDTO bucketDTO = producer.getBucketByUser(email);
            if (bucketDTO == null) {
                bucketDTO = new BucketDTO();
            }
        return bucketDTO;
    }

    @Override
    @Transactional
    public ResponseEntity commitBucketToOrder(String email) {
        try {
            OrderDTO order = producer.commitBucketToOrder(email);
                return new ResponseEntity(order, HttpStatus.OK);
        }catch (NoConnectedToMQException nce){
            return new ResponseEntity(nce.getMessage(),nce.getStatus());
        }catch (ResponseMessageException rme) {
            return new ResponseEntity("Корзина пуста или товар закончился на складе.", HttpStatus.NO_CONTENT);
        }
    }
}
