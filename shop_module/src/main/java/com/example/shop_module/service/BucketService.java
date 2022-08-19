package com.example.shop_module.service;

import com.example.shop_module.domain.Bucket;
import com.example.shop_module.domain.Product;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.BucketDTO;

import java.util.List;

public interface BucketService {

    Bucket createBucket(User user, List<Long> productIds);

    void addProduct(Bucket bucket, List<Long> productIds);

    void removeProduct(Bucket bucket, Product product);

    BucketDTO getBucketByUser (String  email);

    void commitBucketToOrder(String email);


}
