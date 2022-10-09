package com.example.shop_module.service;

import com.example.shop_module.domain.Bucket;
import com.example.shop_module.domain.Product;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.BucketDTO;
import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.exceptions.ResponseMessageException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BucketService {
    BucketDTO getBucketByUser (String  email);
    ResponseEntity commitBucketToOrder(String email);
}
