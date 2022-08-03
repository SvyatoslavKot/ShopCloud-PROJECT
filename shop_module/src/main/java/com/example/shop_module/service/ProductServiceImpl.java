package com.example.shop_module.service;

import com.example.shop_module.domain.Bucket;
import com.example.shop_module.domain.Product;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.mapper.ProductMapper;
import com.example.shop_module.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements  ProductService {


    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    public List<ProductDTO> getAll() {
        return  productRepository.findAll().stream().map(
                product -> new ProductDTO(
                        product.getId(),
                        product.getTitle(),
                        product.getPrice()
                )).collect(Collectors.toList());
    }

    @Override
    public void addToUserBucket(Long productId, String mail) {
        User user = userService.finByMail(mail);
        if (user == null) {
            throw new RuntimeException("User no found " + mail);
        }

        Bucket bucket  = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);

        }else {
            bucketService.addProduct(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        User user = userService.finByMail(mail);
        if (user == null){
            throw  new RuntimeException("User not found " + mail);
        }
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product with id -> " + productId + " not found"));
        Bucket bucket = user.getBucket();
        bucketService.removeProduct(bucket,product);
    }
}
