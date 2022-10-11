package com.example.shop_module.service;

import com.example.shop_module.dto.BucketDTO;
import org.springframework.http.ResponseEntity;

public interface BucketService {
    BucketDTO getBucketByUser (String  email);
    ResponseEntity commitBucketToOrder(String email);
}
