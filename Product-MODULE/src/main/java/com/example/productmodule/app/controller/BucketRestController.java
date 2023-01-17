package com.example.productmodule.app.controller;

import com.example.productmodule.app.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/bucket")
public class BucketRestController {

    private final BucketService bucketService;

    public BucketRestController(BucketService bucketService) {
        this.bucketService = bucketService;
    }
    @GetMapping(value = "/confirm" , produces ="application/json")
    public ResponseEntity confirmOrder (@RequestParam(name = "mail") String mail) {
        try{
            bucketService.commitBucketToOrder(mail);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
}
