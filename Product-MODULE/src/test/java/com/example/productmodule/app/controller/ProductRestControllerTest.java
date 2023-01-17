package com.example.productmodule.app.controller;

import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;


import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    ProductRestController productRestController;

    @Autowired
    ProductRepository productRepository;

    private  Optional<String> nameFilter = java.util.Optional.empty();
    private  Optional<BigDecimal> min = java.util.Optional.empty();
    private  Optional<BigDecimal> max = java.util.Optional.empty();
    private  Optional<Integer> page = java.util.Optional.empty();
    private  Optional<Integer> size =java.util.Optional.empty();
    private  Optional<String> sortFilter = java.util.Optional.empty();
    private  Optional<String> sortOrder = java.util.Optional.empty();

    @Test
    void getByParamDefault() {
        ResponseEntity response =  productRestController.getByParam(nameFilter, min, max, page,size,sortFilter,sortOrder);
        Page<Product> pageProduct = (Page<Product>) response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(pageProduct);
        assertEquals(pageProduct.getNumber(), 0);
        assertEquals(pageProduct.getSize(), 4);
        assertEquals(pageProduct.getContent().size(), 4);
    }

    @Test
    void getByParamNotFound() {
        page = Optional.of(5);
        size = Optional.of(10);
        ResponseEntity response =  productRestController.getByParam(nameFilter, min, max, page,size,sortFilter,sortOrder);
        var message = response.getHeaders().get("message").stream().findFirst().get();

        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
        assertEquals(message, "Not found product by your param!");
    }
    @Test
    void getByParamException() {
        page = Optional.of(0);
        assertThrows(RuntimeException.class, () -> {
            ResponseEntity response =  productRestController.getByParam(nameFilter, min, max, page,size,sortFilter,sortOrder);
        });

    }

    @Test
    void getByParamPageSize() {
        page = Optional.of(2);
        size = Optional.of(2);
        ResponseEntity response =  productRestController.getByParam(nameFilter, min, max, page,size,sortFilter,sortOrder);
        Page<Product> pageProduct = (Page<Product>) response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(pageProduct);
        assertEquals(pageProduct.getNumber(), 1);
        assertEquals(pageProduct.getSize(), 2);
        assertEquals(pageProduct.getContent().size(), 2);
    }

    @Test
    void getByParamNameFilter() {
        nameFilter = Optional.of("B");
        ResponseEntity response =  productRestController.getByParam(nameFilter, min, max, page,size,sortFilter,sortOrder);
        Page<Product> pageProduct = (Page<Product>) response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(pageProduct);
        assertEquals(pageProduct.getNumber(), 0);
        assertEquals(pageProduct.getSize(), 4);
        assertEquals(pageProduct.getContent().size(), 2);
    }

    @Test
    void getByParamMinMax() {
        nameFilter = Optional.of("B");
        min = Optional.of(new BigDecimal(46));


        ResponseEntity responseWithMin =  productRestController.getByParam(nameFilter, min, max, page,size,sortFilter,sortOrder);
        Page<Product> pageProductWithMin = (Page<Product>) responseWithMin.getBody();
        Product productWithMin = pageProductWithMin.getContent().stream().findFirst().get();

        assertEquals(responseWithMin.getStatusCode(), HttpStatus.OK);
        assertNotNull(pageProductWithMin);
        assertEquals(pageProductWithMin.getNumber(), 0);
        assertEquals(pageProductWithMin.getSize(), 4);
        assertEquals(pageProductWithMin.getContent().size(), 1);
        assertEquals(productWithMin.getTitle(), "Bread");
        assertEquals(productWithMin.getPrice(), new BigDecimal(50));

        min = java.util.Optional.empty();
        max = Optional.of(new BigDecimal(46));
        ResponseEntity responseWithMax =  productRestController.getByParam(nameFilter, min, max, page,size,sortFilter,sortOrder);
        Page<Product> pageProductWithMax = (Page<Product>) responseWithMax.getBody();
        Product productWithMax = pageProductWithMax.getContent().stream().findFirst().get();
        assertEquals(responseWithMax.getStatusCode(), HttpStatus.OK);
        assertNotNull(pageProductWithMax);
        assertEquals(pageProductWithMax.getNumber(), 0);
        assertEquals(pageProductWithMax.getSize(), 4);
        assertEquals(pageProductWithMax.getContent().size(), 1);
        assertEquals(productWithMax.getTitle(), "Bear");
        assertEquals(productWithMax.getPrice(), new BigDecimal(45));
    }

    @Test
    void getById() {
        Long id = 1l;

        ResponseEntity responseEntity = productRestController.getById(id);
        ProductDTO productDTO = (ProductDTO) responseEntity.getBody();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(productDTO);
        assertEquals(productDTO.getId(), id);
        assertEquals(productDTO.getTitle(), "Cheese");
    }

    @Test
    void getByIdEror() {
        Long id = 6l;

        ResponseEntity responseEntity = productRestController.getById(id);
        ProductDTO productDTO = (ProductDTO) responseEntity.getBody();
        String message = responseEntity.getHeaders().get("message").stream().findFirst().get();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        assertNotNull(productDTO);
        assertNotNull(message);
        assertEquals(productDTO.getId(), null);
        assertEquals(productDTO.getTitle(), null);
        assertEquals(message,  "Product with id: " + id +" not found!");
    }

    @Test
    @Transactional
    void addProduct() {
        Category category = new Category(6l, "testCategory");
        ProductDTO productDTO = new ProductDTO(null,"TestProduct", new BigDecimal(100),new ArrayList<Category>(List.of(category)),1l);

        ResponseEntity response = productRestController.addProduct(productDTO);


        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(productRepository.findAll().size(),6);
    }
    @Test
    @Transactional
    void addProductException() {
        Category category = new Category(6l, "testCategory");
        ProductDTO productDTO = new ProductDTO(1l,null, null,new ArrayList<Category>(List.of(category)),1l);


        ResponseEntity response = productRestController.addProduct(productDTO);
        var msg = response.getHeaders().get("message").stream().findFirst().get();

        assertEquals(response.getStatusCode(), HttpStatus.RESET_CONTENT);
        assertEquals(productRepository.findAll().size(),5);
        assertEquals(msg,  "Product with id: " + 1l + " is present!");

        productDTO.setId(null);
        ResponseEntity responsePriceNull = productRestController.addProduct(productDTO);
        var msgPriceNull = responsePriceNull.getHeaders().get("message").stream().findFirst().get();

        assertEquals(responsePriceNull.getStatusCode(), HttpStatus.PARTIAL_CONTENT);
        assertEquals(productRepository.findAll().size(),5);
        assertEquals(msgPriceNull,  "Field price is Empty");

        productDTO.setPrice(new BigDecimal(100));

        ResponseEntity responseTitleNull = productRestController.addProduct(productDTO);
        var msgTitleNull = responseTitleNull.getHeaders().get("message").stream().findFirst().get();

        assertEquals(responseTitleNull.getStatusCode(), HttpStatus.PARTIAL_CONTENT );
        assertEquals(productRepository.findAll().size(),5);
        assertEquals(msgTitleNull,"Field title is Empty");

    }



    @Test
    void addToBucket() {
    }

    @Test
    void removeFromBucket() {
    }
}