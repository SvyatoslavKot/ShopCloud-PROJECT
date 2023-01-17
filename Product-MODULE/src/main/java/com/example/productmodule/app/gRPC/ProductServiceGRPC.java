package com.example.productmodule.app.gRPC;


import com.example.grpc.ProductServiceGrpc;
import com.example.grpc.ProductServiceOuterClass;
import com.example.productmodule.app.domain.Bucket;
import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.gRPC.mapper.GRpcMapper;
import com.example.productmodule.app.mq.ProduceClientShop;
import com.example.productmodule.app.repository.BucketRepository;
import com.example.productmodule.app.repository.ProductRepository;
import com.example.productmodule.app.service.BucketService;
import com.example.productmodule.app.service.ProductService;
import com.example.productmodule.app.service.jpaSpecification.ProductSpecification;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@GRpcService
public class ProductServiceGRPC extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private BucketRepository bucketRepository;
    @Autowired
    private BucketService bucketService;
    @Autowired
    @Qualifier("rabbitProduceClientShop")
    private ProduceClientShop clientShopEvent;

    private GRpcMapper gRpcMapper = new GRpcMapper();

 @Override
 @Transactional
 public void getById(ProductServiceOuterClass.MessageId request, StreamObserver<ProductServiceOuterClass.ProtoProduct> responseObserver) {
    log.info("gRPC get By Id Start");
     ProductServiceOuterClass.ProtoProduct protoProduct = null;
    try{
         Product product = productService.getProductById(request.getId());
         protoProduct = gRpcMapper.productToGRpcResponse(product);

     }catch (RuntimeException e){
        protoProduct = gRpcMapper.productToGRpcResponse(new Product(0l,"",new BigDecimal(0),null,new ArrayList<>()));
    }
    responseObserver.onNext(protoProduct);

    responseObserver.onCompleted();
 }

 @Override
 @Transactional
 public void getAllProduct(ProductServiceOuterClass.MessageFindAll request, StreamObserver<ProductServiceOuterClass.ProtoListOfProduct> responseObserver) {
    log.info("gRPC get all Product");
    List<Product> productList = productRepository.findAll();
    List<ProductServiceOuterClass.ProtoProduct> productResponseList = new ArrayList<>();

    for (Product product : productList) {
     productResponseList.add(gRpcMapper.productToGRpcResponse(product));
    }
    ProductServiceOuterClass.ProtoListOfProduct response = ProductServiceOuterClass.ProtoListOfProduct.newBuilder()
            .addAllProduct(productResponseList)
            .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
 }

    @Override
    @Transactional
    public void addProduct(ProductServiceOuterClass.ProtoProduct request, StreamObserver<ProductServiceOuterClass.MessageRequest> responseObserver) {
        log.info("Request -> {}", request.getTitle());

        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setPrice(new BigDecimal(request.getPrice()));

        System.out.println(product);

        productRepository.save(product);

        ProductServiceOuterClass.MessageRequest response = ProductServiceOuterClass.MessageRequest.newBuilder()
                .setMessage("Product Add").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void addProductToBucket(ProductServiceOuterClass.MessageAddToBucket request, StreamObserver<ProductServiceOuterClass.MessageRequest> responseObserver) {
        log.info("Request -> mail:{}, productId:{}", request.getMail(), request.getProductId());

        Bucket bucket = bucketRepository.findBucketByUserMail(request.getMail()).orElse(null);
        if (bucket != null ){
            bucketService.addProduct(bucket, Collections.singletonList(request.getProductId()));
        } else {
            Bucket newBucket = bucketService.createBucket(request.getMail(), Collections.singletonList(request.getProductId()));
            clientShopEvent.addBucketClient(request.getMail(), newBucket.getId());
        }

        ProductServiceOuterClass.MessageRequest response = ProductServiceOuterClass.MessageRequest.newBuilder()
                .setMessage("OK")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    @Transactional
    public void removeProductFromBucket(ProductServiceOuterClass.MessageRemoveFromBucket request, StreamObserver<ProductServiceOuterClass.MessageRequest> responseObserver) {

     log.info("Request -> productId:{}, mail:{}", request.getProductId(), request.getMail());
     Product product = productRepository.findById(request.getProductId()).orElseThrow(()-> new RuntimeException("Product with id -> " + request.getProductId() + " not found"));
     Bucket bucket = bucketRepository.findBucketByUserMail(request.getMail()).orElseThrow(()-> new RuntimeException("Client bucket with name -> " + request.getMail() + " not found"));
     bucketService.removeProduct(bucket,product);

        ProductServiceOuterClass.MessageRequest response = ProductServiceOuterClass.MessageRequest.newBuilder()
                .setMessage("OK {remove product with id:"+request.getProductId()+"}" )
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void getAllByParam(ProductServiceOuterClass.MessageFindAllWithParam request, StreamObserver<ProductServiceOuterClass.ProtoPageableResponse> responseObserver) {
        log.info("requst {}", request);
        Specification<Product> specification = Specification.where(null);

        if(request.getTitle()!=null && !request.getTitle().isEmpty()){
            specification = specification.and(ProductSpecification.titleLike(request.getTitle()));
        }
        if (request.getMin()!= null && !request.getMin().isEmpty()){
            specification = specification.and(ProductSpecification.greaterOrEquals(new BigDecimal(request.getMin())));
        }
        if (request.getMax()!=null && !request.getMax().isEmpty()){
            specification = specification.and(ProductSpecification.lessOrEquals(new BigDecimal(request.getMax())));
        }
        /*if (sortField.isPresent() && sortOrder.isPresent()){
            productRepository.findAll(specification,
                    PageRequest.of(page.orElse(1)-1,size.orElse(4),
                            Sort.by(Sort.Direction.fromString(sortOrder.get()), sortField.get())));
        }

         */
        Page<Product> productPage = productRepository.findAll(specification,
                PageRequest.of(request.getPage() - 1,request.getSize()));
        List<ProductServiceOuterClass.ProtoProduct> protoProductList = new ArrayList<>();
        for (Product product : productPage.getContent()){
            protoProductList.add(gRpcMapper.productToGRpcResponse(product));
        }
        ProductServiceOuterClass.ProtoListOfProduct listOfProduct = ProductServiceOuterClass.ProtoListOfProduct.newBuilder()
                .addAllProduct(protoProductList).build();

        ProductServiceOuterClass.ProtoPageableResponse protoPageableResponse = ProductServiceOuterClass.ProtoPageableResponse.newBuilder()
                .setFirst(productPage.isFirst())
                .setLast(productPage.isLast())
                .setTotalPages(productPage.getTotalPages())
                .setNumberOfElements(productPage.getNumberOfElements())
                .setContent(listOfProduct)
                .setNumber(productPage.getNumber())
                .setSize(productPage.getSize())
                .setTotalElements(5)
                .build();

        log.info("Page -> " + protoPageableResponse);
        log.info("get By Param Ok");

        responseObserver.onNext(protoPageableResponse);
        responseObserver.onCompleted();
    }




}
