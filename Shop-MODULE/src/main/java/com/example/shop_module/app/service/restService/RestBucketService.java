package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.restClient.BucketRestClient;
import com.example.shop_module.app.service.BucketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestBucketService implements BucketService {

    private final BucketRestClient bucketRestClient;

    public RestBucketService(BucketRestClient bucketRestClient) {
        this.bucketRestClient = bucketRestClient;
    }

    @Override
    public ResponseEntity getBucketByUser(String email) {
        return null;
    }

    @Override
    public ResponseEntity commitBucketToOrder(String email) {
        String url =  "/product/confirm" + "?mail=" + email;
        ResponseEntity response = null;
        try {
            response = bucketRestClient.getForEntity(url,Void.class);
            log.info("response -> " + response);
        } catch (NoConnectedToRestService e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
        if (!response.getStatusCode().equals(HttpStatus.OK)){
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            return new ResponseEntity(msg, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return response;
    }
}
