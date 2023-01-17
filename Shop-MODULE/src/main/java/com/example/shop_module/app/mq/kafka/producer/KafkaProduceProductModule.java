package com.example.shop_module.app.mq.kafka.producer;

import com.example.shop_module.app.dto.BucketDTO;
import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.mq.ProduceProductModule;
import com.example.shop_module.app.mq.kafka.KafkaSettings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class KafkaProduceProductModule implements ProduceProductModule {

    private final KafkaProducer kafkaProducer;


    @Override
    public ProductDTO getProductItem(Long id) {
        return null;
    }


    @Override
    public void addToBucketByID(Long productId, String mail) {

        //kafkaTemplate.send()
    }
    @Override
    public void removeFromBucket(Long productId, String mail) {

    }

    @Override
    public void addProduct(ProductDTO dto) {
        kafkaProducer.produce(KafkaSettings.TOPIC_PRODUCT_MODULE_GET_BY_ID.getValue(), dto);
    }




    @Override
    public BucketDTO getBucketByUser(String email) {
        return null;
    }


    @Override
    public List<ProductDTO> getAll() {
        return null;
    }



    @Override
    public OrderDTO commitBucketToOrder(String email) {
        return null;
    }
}
