package com.example.shop_module.service;

import com.example.shop_module.domain.*;
import com.example.shop_module.dto.BucketDTO;
import com.example.shop_module.dto.BucketDetailsDTO;
import com.example.shop_module.mq.Producer;
import com.example.shop_module.repository.BucketRepository;
import com.example.shop_module.repository.OrderDetailsRepo;
import com.example.shop_module.repository.OrderRepository;
import com.example.shop_module.repository.ProductRepository;
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
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepo orderDetailsRepo;
    private final OrderRepository orderRepository;

    @Autowired
    private Producer producer;

    private final UserService userService;
    private final OrderService orderService;

    public BucketServiceImpl(BucketRepository bucketRepository,OrderRepository orderRepository, ProductRepository productRepository, UserService userService, OrderService orderService, OrderDetailsRepo orderDetailsRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.orderService = orderService;
        this.orderRepository =orderRepository;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
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
    public void removeProduct(Bucket bucket, Product product) {
        List<Product> products = bucket.getProducts();
        products.remove(product);
        bucket.setProducts(products);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String email) {
        User user = userService.finByMail(email);
        if(user == null || user.getBucket() == null) {
            return new BucketDTO();
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailsDTO> mapByProductId = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();
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
    @Transactional
    public void commitBucketToOrder(String email){
        User user = userService.finByMail(email);
        if (user == null) {
            throw  new RuntimeException("User is not found");
        }
        Bucket bucket = user.getBucket();
        if (bucket == null || bucket.getProducts().isEmpty()){
            return;
        }

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrdersDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> (orderDetailsRepo.save(new OrdersDetails(order, pair.getKey(), pair.getValue()))))
                .collect(Collectors.toList());


        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setSum(total);
        order.setAddress("none");


        order.setOrders_details(new ArrayList<>());
        Order ordersave = orderRepository.save(order);
        ordersave.getOrders_details().addAll(orderDetails);
        producer.sendOrder(ordersave);


        //orderService.saveOrder(order);

        bucket.getProducts().clear();
        bucketRepository.save(bucket);
    }
}
