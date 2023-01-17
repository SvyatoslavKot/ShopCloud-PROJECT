package com.example.productmodule.app.mq;

import com.example.productmodule.app.dto.BucketDTO;
import com.example.productmodule.app.dto.OrderDTO;
import com.example.productmodule.app.dto.ProductDTO;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;
import java.util.Map;

public interface ShopModuleListener {

    ProductDTO consume2(String id);
    void addProductToBucket(Map<String,Object> msgMap);
    BucketDTO getBucketByMail( String eMail);
    void removeFromBucket (Map<String, Object> requestMap);
    List<ProductDTO> getAll (String msg);
    OrderDTO confirmOrderFromBucket (String mail);
}
