package com.example.productmodule.app.service;

import com.example.productmodule.app.domain.Bucket;
import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.BucketDTO;
import com.example.productmodule.app.repository.BucketRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
class BucketServiceImplTest {
    @Autowired
    BucketServiceImpl bucketService;
    @Autowired
    BucketRepository bucketRepository;

    private String mail= "test@mail";
    private List<Long> productIds = new ArrayList<>(List.of(1l, 2l));

    @BeforeEach
    public void before () {
        if (!bucketRepository.findBucketByUserMail(mail).isPresent()) {
            bucketService.createBucket(mail,productIds);
        }
    }

    @AfterEach
    public void after() {
        if (bucketRepository.findBucketByUserMail(mail).isPresent()) {
            bucketRepository.delete(bucketRepository.findBucketByUserMail(mail).get());
        }
    }

    @Test
    void createBucket() {
        String createTestMail = "createTestMail";
        List<Long> productIds = new ArrayList<>(List.of(3l, 4l));
        bucketService.createBucket(createTestMail,productIds);
        Bucket bucketFromDB = bucketRepository.findBucketByUserMail(createTestMail).get();
        assertNotNull(bucketFromDB);
        bucketRepository.delete(bucketFromDB);
    }

    @Test
    @Transactional
    void addProduct() {
        Bucket bucket = bucketRepository.findBucketByUserMail(mail).get();
        assertNotNull(bucket);
        ArrayList<Long> id = new ArrayList<>();
        id.add(3l);
        bucketService.addProduct(bucket,id);

        Bucket bucketFromDB = bucketRepository.findBucketByUserMail(mail).get();
        assertEquals(bucketFromDB.getProducts().size(), 3);
    }

    @Test
    @Transactional
    void getBucketByUser() {
        BucketDTO bucketDTO = bucketService.getBucketByUser(mail);
        assertNotNull(bucketDTO);
    }

    @Test
    @Transactional
    void getBucketByNewUser() {
        String newUser = "newTestUser";
        BucketDTO bucketDTO = bucketService.getBucketByUser(mail);
        assertNotNull(bucketDTO);
    }

    @Test
    void removeProduct() {
        Bucket bucket = bucketRepository.findBucketByUserMail(mail).get();
        Product cheese = new Product();
        for (Product p : bucket.getProducts()) {
            if (p.getId() == 1) {
                cheese = p;

            }
        }
        bucketService.removeProduct(bucket,cheese);
        assertEquals(bucketRepository.findBucketByUserMail(mail).get().getProducts().size(), 1);
    }

    @Test
    void commitBucketToOrder() {
    }

}