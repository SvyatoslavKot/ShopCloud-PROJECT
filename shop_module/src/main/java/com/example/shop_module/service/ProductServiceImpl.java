package com.example.shop_module.service;

import com.example.shop_module.domain.Bucket;
import com.example.shop_module.domain.Category;
import com.example.shop_module.domain.Product;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import com.example.shop_module.mq.ProduceProductModule;
import com.example.shop_module.repository.ProductRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements  ProductService {


    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final SimpMessagingTemplate templateMsg;
    private final ProduceProductModule produceProductModule;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, SimpMessagingTemplate templateMsg, ProduceProductModule produceProductModule) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.templateMsg = templateMsg;
        this.produceProductModule = produceProductModule;
    }



    @Override
    public void addToUserBucket(Long productId, String mail) {
        produceProductModule.addToBucketByID( productId, mail);
    }
    @Override
    public void removeFromBucket(Long productId, String mail) {
        produceProductModule.removeFromBucket(productId, mail);
    }

    @Override
    public ResponseEntity getById(Long id) throws ResponseMessageException {
        try {
            ProductDTO dto = produceProductModule.getProductItem(id);
            return new ResponseEntity(dto, HttpStatus.OK);
        }catch (NoConnectedToMQException e){
            return new ResponseEntity(e.getMessage(),e.getStatus());
        }catch (ResponseMessageException rme) {
            return new ResponseEntity(rme.getMessage(), rme.getStatus());
        }
    }

    // TODO: 009 09.10.22 change to DTO
    @Override
    public List<ProductDTO> getAll() {
        return  productRepository.findAll().stream().map(
                product -> new ProductDTO(
                        product.getId(),
                        product.getTitle(),
                        product.getPrice()
                )).collect(Collectors.toList());
    }

    // TODO: 009 09.10.22 change to DTO and message
    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO) {
        // TODO: 009 09.10.22 add message
        Product product = new Product(
                productDTO.getId(),
                productDTO.getTitle(),
                productDTO.getPrice(),
                new ArrayList<Category>());
        Product savedProduct = productRepository.save(product);


        ProductDTO dto = new ProductDTO().builder()
                                .id(savedProduct.getId())
                                .price(savedProduct.getPrice())
                                .title(savedProduct.getTitle()).build();
        templateMsg.convertAndSend("/topic/products", dto);
    }

}
