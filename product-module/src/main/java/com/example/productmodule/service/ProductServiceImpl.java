package com.example.productmodule.service;

import com.example.productmodule.domain.Bucket;
import com.example.productmodule.domain.Category;
import com.example.productmodule.domain.Product;
import com.example.productmodule.domain.ShopClient;
import com.example.productmodule.dto.ProductDTO;
import com.example.productmodule.rabbitMq.ProduceClientShop;
import com.example.productmodule.repository.BucketRepository;
import com.example.productmodule.repository.ProductRepository;
import com.example.productmodule.service.jpaSpecification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BucketRepository bucketRepository;
    private final BucketService bucketService;
    private final ProduceClientShop clientShopEvent;

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Page<Product> getByParams(Optional<String> nameFilter,
                                     Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max,
                                     Optional<String> sortField,
                                     Optional<String> sortOrder){

        Specification<Product> specification = Specification.where(null);
        if(nameFilter.isPresent()){
            specification = specification.and(ProductSpecification.titleLike(nameFilter.get()));
        }
        if (min.isPresent()){
            specification = specification.and(ProductSpecification.greaterOrEquals(min.get()));
        }
        if (max.isPresent()){
            specification = specification.and(ProductSpecification.lessOrEquals(max.get()));
        }
        if (sortField.isPresent() && sortOrder.isPresent()){
            productRepository.findAll(specification,
                    PageRequest.of(page.orElse(1)-1,size.orElse(4),
                            Sort.by(Sort.Direction.fromString(sortOrder.get()), sortField.get())));
        }

        return productRepository.findAll(specification,
                PageRequest.of(page.orElse(1)-1,size.orElse(4)));
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

        Bucket bucket = bucketRepository.findBucketByUserMail(mail).orElse(null);
        if (bucket != null ){
            bucketService.addProduct(bucket, Collections.singletonList(productId));
        } else {
            Bucket newBucket = bucketService.createBucket(mail, Collections.singletonList(productId));
            clientShopEvent.addBucketClient(mail, newBucket.getId());
        }
    }



    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getId(),
                productDTO.getTitle(),
                productDTO.getPrice(),
                new ArrayList<Category>());
        System.out.println(product);
        productRepository.save(product);
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product with id -> " + productId + " not found"));
        Bucket bucket = bucketRepository.findBucketByUserMail(mail).orElseThrow(()-> new RuntimeException("Client bucket with name -> " + mail + " not found"));
        bucketService.removeProduct(bucket,product);
    }

}
