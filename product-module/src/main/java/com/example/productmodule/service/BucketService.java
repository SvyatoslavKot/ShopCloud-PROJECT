package com.example.productmodule.service;

import com.example.productmodule.domain.Bucket;
import com.example.productmodule.domain.Product;
import com.example.productmodule.domain.ShopClient;
import com.example.productmodule.dto.BucketDTO;
import com.example.productmodule.dto.OrderDTO;

import java.util.List;

public interface BucketService {


   Bucket createBucket(String mail, List<Long> productIds);
   void addProduct(Bucket bucket, List<Long> productIds);
   BucketDTO getBucketByUser (String  email);
   void removeProduct(Bucket bucket, Product product);
   OrderDTO commitBucketToOrder(String email);
}
