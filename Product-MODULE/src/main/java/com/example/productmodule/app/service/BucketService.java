package com.example.productmodule.app.service;

import com.example.productmodule.app.domain.Bucket;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.BucketDTO;
import com.example.productmodule.app.dto.OrderDTO;

import java.util.List;

public interface BucketService {


   Bucket createBucket(String mail, List<Long> productIds);
   void addProduct(Bucket bucket, List<Long> productIds);
   BucketDTO getBucketByUser (String  email);
   void removeProduct(Bucket bucket, Product product);
   void commitBucketToOrder(String email);
}
