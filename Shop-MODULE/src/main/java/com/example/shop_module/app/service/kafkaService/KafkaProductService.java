package com.example.shop_module.app.service.kafkaService;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.kafka.KafkaSettings;
import com.example.shop_module.app.service.abstraction.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class KafkaProductService extends KafkaAbstractService implements ProductService {

    public KafkaProductService(KafkaTemplate kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public ResponseEntity getAll() {
        return null;
    }

    @Override
    public ResponseEntity getById(Long id) throws ResponseMessageException {
        return null;
    }

    @Override
    public ResponseEntity getByParam(Optional<Integer> page, Optional<Integer> size, Optional<String> title, Optional<BigDecimal> min, Optional<BigDecimal> max) {
        return null;
    }

    @Override
    public void addToUserBucket(Long productId, String mail) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("productId", productId);
        requestMap.put("mail", mail);
        producer.produce(KafkaSettings.TOPIC_PRODUCT_MODULE_ADD_BUCKET_BY_MAIL.getValue(), requestMap);
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("productId", productId);
        requestMap.put("mail", mail);
        producer.produce(KafkaSettings.TOPIC_PRODUCT_MODULE_REMOVE_FROM_BUCKET.getValue(), requestMap);
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        producer.produce(KafkaSettings.TOPIC_PRODUCT_MODULE_GET_BY_ID.getValue(), productDTO);
    }

    @Override
    public void updateProduct(ProductDTO updateProduct) {
        producer.produce(KafkaSettings.TOPIC_PRODUCT_MODULE_UPDATE_PRODUCT.getValue(), updateProduct);
    }

    @Override
    public void addProductFromFile(String name, MultipartFile file) {

    }
}
