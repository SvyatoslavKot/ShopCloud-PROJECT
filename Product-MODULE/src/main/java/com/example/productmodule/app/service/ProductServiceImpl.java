package com.example.productmodule.app.service;

import com.example.productmodule.app.mq.ProduceClientShop;
import com.example.productmodule.app.repository.BucketRepository;
import com.example.productmodule.app.repository.ProductRepository;
import com.example.productmodule.app.domain.Bucket;
import com.example.productmodule.app.domain.Category;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.mq.rabbitMq.RabbitProduceClientShop;
import com.example.productmodule.app.service.jpaSpecification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BucketRepository bucketRepository;
    private final BucketService bucketService;
    private final ProduceClientShop clientShopEvent;

    public ProductServiceImpl(ProductRepository productRepository, BucketRepository bucketRepository, BucketService bucketService,@Qualifier("rabbitProduceClientShop") ProduceClientShop clientShopEvent) {
        this.productRepository = productRepository;
        this.bucketRepository = bucketRepository;
        this.bucketService = bucketService;
        this.clientShopEvent = clientShopEvent;
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with id: " + id + " not found!"));
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
            return productRepository.findAll(specification,
                    PageRequest.of(page.orElse(1)-1,size.orElse(4),
                            Sort.by(Sort.Direction.fromString(sortOrder.get()), sortField.get())));
        }


        System.out.println("page -> " + page + " " + " size -> " + size);
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


    // TODO: 019 19.12.22 countofstock 
    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = new Product(
                productDTO.getId(),
                productDTO.getTitle(),
                productDTO.getPrice(),
                productDTO.getCount_in_stock(),
                new ArrayList<Category>());
        System.out.println(product);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void removeFromBucket(Long productId, String mail) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product with id -> " + productId + " not found"));
        Bucket bucket = bucketRepository.findBucketByUserMail(mail).orElseThrow(()-> new RuntimeException("Client bucket with name -> " + mail + " not found"));
        bucketService.removeProduct(bucket,product);
    }

}
