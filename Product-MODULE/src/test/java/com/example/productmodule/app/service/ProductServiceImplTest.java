package com.example.productmodule.app.service;

import com.example.productmodule.app.domain.Bucket;
import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.repository.BucketRepository;
import com.example.productmodule.app.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BucketServiceImpl bucketService;

    private Product product = new Product(6l, "testTitle", new BigDecimal(100.0), null, new ArrayList<Category>());;
    private Product testProductMinMax = new Product(7l, "testMinMax", new BigDecimal(555.0), null, new ArrayList<Category>());;
    private Product testProductMaxMin = new Product(8l, "testMaxMin", new BigDecimal(999.0), null, new ArrayList<Category>());;
    private String userMail = "test@mail";
    private List<Long> testListId = new ArrayList<>(List.of(1l,2l,3l));

    @BeforeEach
    public void before () {
        if (bucketRepository.findBucketByUserMail(userMail).isPresent()){
            bucketRepository.delete(bucketRepository.findBucketByUserMail(userMail).get());
        }
    }

    @Test
    @Transactional
    void getById() {
        Product product = productService.getById(5l).get();
        assertNotNull(product);
    }

    @Test
    void getProductById() {
        Product product = productService.getProductById(1l);
        BigDecimal price = new BigDecimal(450);
        assertNotNull(product);
        assertEquals(product.getTitle(),"Cheese");
        assertEquals(product.getPrice(), price);
        assertEquals(product.getCount_in_stock() , 10l);

    }

    @Test
    void getByParamsWithoutParam() {
       Page<Product> page = productService.getByParams(java.util.Optional.empty(),java.util.Optional.empty(),java.util.Optional.empty(),
               java.util.Optional.empty(),java.util.Optional.empty(),java.util.Optional.empty(),java.util.Optional.empty());
       assertEquals(4, page.getSize());
       assertEquals(0, page.getNumber());
    }

    @Test
    void getByParamsMinParam() {
        Page<Product> page = productService.getByParams(java.util.Optional.empty(),Optional.of(2),Optional.of(2),
                java.util.Optional.empty(),java.util.Optional.empty(),java.util.Optional.empty(),java.util.Optional.empty());
        assertEquals(2, page.getSize());
        assertEquals(1, page.getNumber());
    }

    @Test
    void getByParamsNameFilter() {
        Optional<String> nameFilter = Optional.of("Cheese");
        Page<Product> page = productService.getByParams(nameFilter,java.util.Optional.empty(),
                java.util.Optional.empty(), java.util.Optional.empty(),java.util.Optional.empty(),
                java.util.Optional.empty(),java.util.Optional.empty());
        List<Product> content = page.getContent();
        Product contentProduct = content.stream().findFirst().get();

        assertEquals(4, page.getSize());
        assertEquals(0, page.getNumber());
        assertEquals(content.size(), 1);
        assertEquals(contentProduct.getTitle(), nameFilter.get());
    }

    @Test
    void getByParamsMinMax() {
        Optional<BigDecimal> min = Optional.of(new BigDecimal(500));
        Optional<BigDecimal> max = Optional.of(new BigDecimal(1000));
        productRepository.save(testProductMinMax);
        productRepository.save(testProductMaxMin);
        Product productMin = null;
        Product productMax = null;

        Page<Product> page = productService.getByParams(java.util.Optional.empty(),java.util.Optional.empty(),
                java.util.Optional.empty(), min,max, java.util.Optional.empty(),java.util.Optional.empty());

        List<Product> content = page.getContent();
        for (Product product : content) {
            if (product.getPrice().equals(testProductMinMax.getPrice()) ) {
                productMin = product;
            }
            if (product.getPrice().equals(testProductMaxMin.getPrice()) ) {
                productMax = product;
            }
        }

        assertEquals(4, page.getSize());
        assertEquals(0, page.getNumber());
        assertEquals(content.size(), 2);
        assertNotNull(productMin);
        assertNotNull(productMax);

        productRepository.delete(productMin);
        productRepository.delete(productMax);
    }

    @Test
    void getAll() {
        List<ProductDTO> productFromDb = productService.getAll();

        assertNotNull(productFromDb);
        assertEquals(productFromDb.size(), 5);

    }

    @Test
    void addToUserBucket() {
        int size;

        bucketService.createBucket(userMail,testListId);

        productService.addToUserBucket(4l,userMail);
        Bucket bucket = bucketRepository.findBucketByUserMail(userMail).orElse(null);

        size = bucket.getProducts().size();

        assertNotNull(bucket);
        assertEquals(size, 4);

        bucketRepository.delete(bucket);
    }

    @Test
    void addProduct() {
        productService.addProduct( new ProductDTO(product) );
        List<Product> productsFromDB = productRepository.findAll();
        Product testProductFromDB = null;

        for (Product product : productsFromDB) {
            if (product.getTitle().equals(product.getTitle())){
                testProductFromDB = product;
            }
        }
        assertNotNull(productsFromDB);
        assertEquals(productsFromDB.size(), 6);
        assertFalse(productsFromDB.size() == 5);
        assertNotNull(testProductFromDB);
        assertEquals(testProductFromDB.getPrice(), product.getPrice());
        assertEquals(testProductFromDB.getCount_in_stock(), product.getCount_in_stock());

        productRepository.delete(testProductFromDB);
    }

    @Test

    void removeFromBucket() {
        bucketService.createBucket(userMail,testListId);

        productService.removeFromBucket(2l,userMail);
        productService.removeFromBucket(2l,userMail);

        Bucket bucketFromDB = bucketRepository.findBucketByUserMail(userMail).get();

        assertEquals(bucketFromDB.getProducts().size(), 2);

        bucketRepository.delete(bucketRepository.findBucketByUserMail(userMail).get());
    }

    @Test
    void removeFromBucketException() {
        var productId = 10l;
        var exceptionMail = "exception@mail";
        List<Long> prodId = new ArrayList<>(List.of(1l,2l,3l));


        bucketService.createBucket(userMail,prodId);

        assertThrows(RuntimeException.class, () ->{
            productService.removeFromBucket(productId,userMail);
        });
        assertThrows(RuntimeException.class, () -> {
            productService.removeFromBucket(bucketRepository.findBucketByUserMail(userMail).get().getId(),exceptionMail);
        });

        bucketRepository.delete(bucketRepository.findBucketByUserMail(userMail).get());
    }
}