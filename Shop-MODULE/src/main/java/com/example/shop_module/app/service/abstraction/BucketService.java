package com.example.shop_module.app.service.abstraction;

import com.example.shop_module.app.dto.BucketDTO;
import org.springframework.http.ResponseEntity;

public interface BucketService {
    ResponseEntity getBucketByUser (String  email);

    ResponseEntity commitBucketToOrder(String email);
}
