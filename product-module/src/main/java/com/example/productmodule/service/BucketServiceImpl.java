package com.example.productmodule.service;

import com.example.productmodule.domain.*;
import com.example.productmodule.dto.BucketDTO;
import com.example.productmodule.dto.BucketDetailsDTO;
import com.example.productmodule.dto.OrderDTO;
import com.example.productmodule.dto.OrderDetailsDto;
import com.example.productmodule.repository.BucketRepository;
import com.example.productmodule.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
   // private final ProductService productService;

    @Override
    @Transactional
    public Bucket createBucket(String userMail, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUserMail(userMail);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)// getOne  вытаскивает ссылку на объект , findeById вытаскивает сам объект
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String email) {
        Bucket bucket = bucketRepository.findBucketByUserMail(email).orElse(new Bucket());

        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailsDTO> mapByProductId = new HashMap<>();
        List<Product> products = bucket.getProducts();
        for (Product product : products) {
            BucketDetailsDTO details = mapByProductId.get(product.getId());
            if (details == null) {
                mapByProductId.put(product.getId(), new BucketDetailsDTO(product));
            }else {
                details.setAmount(details.getAmount().add(new BigDecimal(1.0)));
                details.setSum(details.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }


    @Override
    public void removeProduct(Bucket bucket, Product product) {
        List<Product> products = bucket.getProducts();
        products.remove(product);
        bucket.setProducts(products);
        bucketRepository.save(bucket);
    }


    @Override
    @Transactional
    public OrderDTO commitBucketToOrder(String email){
        Bucket bucket = bucketRepository.findBucketByUserMail(email).orElseThrow(() -> new RuntimeException("Bucket is not found"));

        OrderDTO orderDTO = new OrderDTO();

       // for (Product product : bucket.getProducts()){
            //if (productService.bockInStock(product)){
                //Map<Product, Long ......
            //}
        //}

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetailsDto> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> (new OrderDetailsDto( pair.getKey(), pair.getValue())))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        orderDTO.setStatus(OrderStatus.NEW.name());
        orderDTO.setSum(total);
        orderDTO.setAddress("none");
        orderDTO.setOrderDetails(orderDetails);
        orderDTO.setUserMail(email);

        return orderDTO;
    }


}
