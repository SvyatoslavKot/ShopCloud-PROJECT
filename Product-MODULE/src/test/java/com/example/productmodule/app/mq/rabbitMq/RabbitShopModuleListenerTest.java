package com.example.productmodule.app.mq.rabbitMq;

import com.example.productmodule.app.domain.Bucket;
import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.BucketDTO;
import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.repository.BucketRepository;
import com.example.productmodule.app.repository.ProductRepository;
import com.example.productmodule.app.service.BucketServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class RabbitShopModuleListenerTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BucketRepository bucketRepository;
    @Autowired
    private BucketServiceImpl bucketService;

    @Autowired
    private RabbitShopModuleListener rabbitShopModuleListener;

    private String testMail = "Test@mail";

    @BeforeEach
    void before () {
        List<Long> listProductId = new ArrayList<>(List.of(1l,2l,3l));
        bucketService.createBucket(testMail, listProductId);
    }

    @AfterEach
    void after () {
       bucketRepository.delete( bucketRepository.findBucketByUserMail(testMail).get() );
    }

    @Test
    void consume2() {
        ProductDTO productDTO = rabbitShopModuleListener.consume2("1");

        assertNotNull(productDTO);
        assertEquals(productDTO.getPrice(), new BigDecimal(450));
        assertEquals(productDTO.getTitle(), "Cheese");
        assertEquals(productDTO.getCount_in_stock(), 10l);
    }

    @Test
    void consume2getNewId() {
        ProductDTO productDTO = rabbitShopModuleListener.consume2("44");

        assertNotNull(productDTO);
        assertTrue(productDTO.getId() == null);
        assertTrue(productDTO.getTitle() == null);
        assertTrue(productDTO.getCount_in_stock() == null);
        assertTrue(productDTO.getCategories() == null);
    }

    @Test
    void consume2IdNoConvert() {
        ProductDTO productDTO = rabbitShopModuleListener.consume2("aa");
        assertNotNull(productDTO);
        assertTrue(productDTO.getId() == null);
        assertTrue(productDTO.getTitle() == null);
        assertTrue(productDTO.getCount_in_stock() == null);
        assertTrue(productDTO.getCategories() == null);
    }

    @Test
    @Transactional
    void addProductToBucket() {


        Map<String,Object> msgMap = new HashMap<>();
        Integer newProductId = 4;
        msgMap.put("mail", testMail);
        msgMap.put("id", newProductId);
        Product productFromDb = null;

        rabbitShopModuleListener.addProductToBucket(msgMap);
        Bucket bucket = bucketRepository.findBucketByUserMail(testMail).get();

        for (Product product : bucket.getProducts()){
            if (product.getId().equals(4l)) {
                productFromDb = product;
            }
        }

        assertNotNull(bucket);
        assertNotNull(productFromDb);
        assertEquals(bucket.getProducts().size(), 4);
        assertEquals(productFromDb.getId(), 4l);
        assertEquals(productFromDb.getTitle(), "Tomato");
        assertEquals(productFromDb.getPrice(), new BigDecimal(150));
        assertEquals(productFromDb.getCount_in_stock(), 10l);

    }

    @Test
    void getBucketByMail() {
        BucketDTO bucketDTO = rabbitShopModuleListener.getBucketByMail(testMail);
        assertNotNull(bucketDTO);
        assertTrue(bucketDTO.getBucketDetails().size() == 3);
        assertTrue(bucketDTO.getAmountProducts() == 3);
        assertTrue(bucketDTO.getSum() == 570);
    }

    @Test
    void removeFromBucket() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("mail", testMail);
        requestMap.put("productId", 1);

        rabbitShopModuleListener.removeFromBucket(requestMap);

        Bucket bucket = bucketRepository.findBucketByUserMail(testMail).get();

        assertEquals(bucket.getProducts().size(), 2);
    }

    @Test
    void getAll() {
        String anyMsg = "msg";

        List<ProductDTO> productList = rabbitShopModuleListener.getAll(anyMsg);

        assertNotNull(productList);
        assertEquals(productList.size(), 5);
    }

    @Test
    void productAdd() {
        ProductDTO dto = new ProductDTO();
        dto.setTitle("testProduct");
        dto.setCount_in_stock(10l);
        dto.setPrice(new BigDecimal(100));
        dto.setCategories(new ArrayList<Category>());

        rabbitShopModuleListener.productAdd(dto);

        Product product = null;
        for (Product pr : productRepository.findAll()){
            if (pr.getTitle().equals(dto.getTitle())){
                product = pr;
            }
        }

        assertNotNull(product);
        assertEquals(product.getPrice(), dto.getPrice());

        productRepository.delete(product);

    }

    @Test
    void confirmOrderFromBucket() {
    }
}