package com.example.shop_module.app.service.grpcService;

import com.example.shop_module.app.service.abstraction.BucketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcBucketService implements BucketService {

    @Override
    public ResponseEntity getBucketByUser(String email) {
        return null;
    }

    @Override
    public ResponseEntity commitBucketToOrder(String email) {
        return null;
    }
}
