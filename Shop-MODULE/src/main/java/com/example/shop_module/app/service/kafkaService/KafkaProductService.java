package com.example.shop_module.app.service.kafkaService;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceProductModule;
import com.example.shop_module.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@Slf4j
public class KafkaProductService implements ProductService {

    @Autowired
    @Qualifier("kafkaProduceProductModule")
    private ProduceProductModule produceProductModule;



    @Override
    public ResponseEntity getAll() {
        return null;
    }

    @Override
    public ResponseEntity getById(Long id) throws ResponseMessageException {
        return null;
    }

    @Override
    public ResponseEntity getByParam(Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<String> title,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max) {
        return null;
    }

    @Override
    public void addToUserBucket(Long productId, String mail) {
        produceProductModule.addToBucketByID(productId,mail);
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        produceProductModule.removeFromBucket(productId, mail);
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        produceProductModule.addProduct(productDTO);
    }

    @Override
    public void updateProduct(ProductDTO updateProduct) {
        produceProductModule.updateProduct(updateProduct);
    }
}
