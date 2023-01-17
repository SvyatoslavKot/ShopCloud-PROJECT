package com.example.productmodule.app.mq.rabbitMq;

import com.example.productmodule.app.mq.ShopModuleListener;
import com.example.productmodule.app.service.BucketService;
import com.example.productmodule.app.service.ProductService;
import com.example.productmodule.app.dto.BucketDTO;
import com.example.productmodule.app.dto.OrderDTO;
import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.dto.mappers.ProductMapperDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class RabbitShopModuleListener implements ShopModuleListener {

    private final ProductService productService;
    private final BucketService bucketService;
    private final ProductMapperDTO productMapperDTO = new ProductMapperDTO();

    @RabbitListener(queues = "queue-productGet")
    @Transactional
    public ProductDTO consume2(@Payload String id) {
        try{
            if (productService.getById(Long.valueOf(id)).isPresent()){
                return  productMapperDTO.productToDTO(productService.getById(Long.valueOf(id)).get());
            }
        } catch (NumberFormatException e){
            log.error("id can't convert string to long -> {}", e.getMessage());
        }
        return new ProductDTO();
    }

    @RabbitListener(queues = "queue-prAddBucket")
    @Transactional
    public void addProductToBucket(@Payload Map<String,Object> msgMap) {
        System.out.println(msgMap.get("mail"));
        System.out.println(msgMap.get("id").getClass());
        String mail = (String) msgMap.get("mail");
        Integer productId = (Integer) msgMap.get("id");

        productService.addToUserBucket(Long.valueOf(productId),mail);
    }

    @RabbitListener(queues = "queue-prGetBucket")
    @Transactional
    public BucketDTO getBucketByMail(@Payload String eMail) {
        return  bucketService.getBucketByUser(eMail);
    }

    @RabbitListener(queues = "queue-prRemoveFromBucket")
    public void removeFromBucket (@Payload Map<String, Object> requestMap) {
        String mail = (String) requestMap.get("mail");
        Integer productId = (Integer) requestMap.get("productId");
        productService.removeFromBucket(Long.valueOf(productId),mail);
    }

    @RabbitListener(queues = "queue-prGetAll")
    public List<ProductDTO> getAll (@Payload String msg) {
        return productService.getAll();
    }

    @RabbitListener(queues = "queue-prAdd")
    public void productAdd (@Payload ProductDTO dto) {
        System.out.println("dto -> " + dto);
        productService.addProduct(dto);
    }


    @RabbitListener(queues = "queue-prConfirmOrder")
    @Transactional
    public OrderDTO confirmOrderFromBucket (@Payload String mail) {
       // OrderDTO dto = bucketService.commitBucketToOrder(mail);
       // System.out.println("dto -> " + dto);
        return null;
    }
}
