package com.example.shop_module.app.service.grpcService;

import com.example.grpc.ProductServiceGrpc;
import com.example.grpc.ProductServiceOuterClass;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToMQException;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.gRPC.mapper.ProductGRpcMapper;
import com.example.shop_module.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class GrpcProductService implements ProductService {

    @Autowired
    private ProductServiceGrpc.ProductServiceBlockingStub stub;
    private ProductGRpcMapper productMapper = new ProductGRpcMapper();

    @Override
    @Transactional
    public ResponseEntity getAll() throws NoConnectedToMQException, ResponseMessageException {
        ProductServiceOuterClass.MessageFindAll request = ProductServiceOuterClass.MessageFindAll.newBuilder().build();
        List<ProductDTO> productDTOList = new ArrayList<>();

        ProductServiceOuterClass.ProtoListOfProduct productList = null;

        try{
            productList = stub.getAllProduct(request);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Some problem with gRpc Server");
        }

        if( productList != null && !productList.getProductList().isEmpty() ){
            for (ProductServiceOuterClass.ProtoProduct grpcProduct : productList.getProductList()) {
                ProductDTO productDTO = productMapper.fromGRpc(grpcProduct);
                productDTOList.add(productDTO);
            }
            return new ResponseEntity(productDTOList, HttpStatus.OK);
        }
        return new ResponseEntity("No Content", HttpStatus.NO_CONTENT );
    }

    @Override
    @Transactional
    public ResponseEntity getById(Long id) throws RuntimeException {
        ProductServiceOuterClass.MessageId request = ProductServiceOuterClass.MessageId.newBuilder()
                .setId(id)
                .build();
        ProductServiceOuterClass.ProtoProduct protoProduct = null;

        try{
            protoProduct = stub.getById(request);
        }catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            throw new NoConnectedToGRpsServer();
        }

        if (protoProduct.getId() != 0 ){
            log.info("Request -> {}, Response -> {}", request, protoProduct);
            ProductDTO productDTO = productMapper.fromGRpc(protoProduct);
            return new ResponseEntity(productDTO, HttpStatus.OK);
        }
        return new ResponseEntity("No Content for your parameters -> id:{" + id + "}", HttpStatus.NO_CONTENT );
    }

    @Override
    @Transactional
    public ResponseEntity getByParam(Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<String> title,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max) {
        log.info("Requester run");
        ProductServiceOuterClass.MessageFindAllWithParam request = ProductServiceOuterClass.MessageFindAllWithParam.newBuilder()
                .setPage(page.orElse(1))
                .setSize(size.orElse(4))
                .setTitle(title.orElse(""))
                .setMin(min.orElse(BigDecimal.valueOf(1)).toString())
                .setMax(max.orElse(BigDecimal.valueOf(99999*9)).toString())
                .build();
        log.info("request -> {}", request);

        ProductServiceOuterClass.ProtoPageableResponse protoPageableResponse = null;

        try{
            protoPageableResponse = stub.getAllByParam(request);
        }catch (RuntimeException r){
            log.error(r.getMessage(),r.getCause(),r.getLocalizedMessage());
            throw new NoConnectedToGRpsServer();
        }

        if (!protoPageableResponse.getContent().getProductList().isEmpty()){
            Page<ProductDTO> productPage = productMapper.pageFromGRpc(protoPageableResponse);
            return new ResponseEntity(productPage, HttpStatus.OK);
        }
        return new ResponseEntity("No Content", HttpStatus.NO_CONTENT );
    }

    @Override
    public void addToUserBucket(Long productId, String mail) {

        ProductServiceOuterClass.MessageAddToBucket request = ProductServiceOuterClass.MessageAddToBucket.newBuilder()
                .setProductId(productId)
                .setMail(mail)
                .build();
        ProductServiceOuterClass.MessageRequest response = null;

        try{
            response = stub.addProductToBucket(request);
            log.info("Product add to bucket Response -> {}" ,response.getMessage());
        }catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            throw new NoConnectedToGRpsServer();
        }


    }

    @Override
    public void removeFromBucket(Long productId, String mail) throws RuntimeException {
        ProductServiceOuterClass.MessageRemoveFromBucket request = ProductServiceOuterClass.MessageRemoveFromBucket.newBuilder()
                .setProductId(productId)
                .setMail(mail)
                .build();
        ProductServiceOuterClass.MessageRequest response = null;

        try{
            response = stub.removeProductFromBucket(request);
            log.info("Response -> {}", response);
        }catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            throw new NoConnectedToGRpsServer();
        }
    }


    @Override
    public void addProduct(ProductDTO productDTO) {
        ProductServiceOuterClass.ProtoProduct protoProduct = productMapper.toProtoProduct(productDTO);
        ProductServiceOuterClass.MessageRequest response = null;

        try{
            response = stub.addProduct(protoProduct);
            log.info("Response -> {}", response);
        }catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            throw new NoConnectedToGRpsServer();
        }
    }
}
