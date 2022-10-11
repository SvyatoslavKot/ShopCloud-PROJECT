package com.example.shop_module.service;

import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import com.example.shop_module.mq.ProduceProductModule;
import com.example.shop_module.wrapper.PageableResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements  ProductService {

    private final SimpMessagingTemplate templateMsg;
    private final ProduceProductModule produceProductModule;
    private final RestTemplate restTemplate;

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

    @Override
    public ResponseEntity getAll() {
        try{
            var productList = produceProductModule.getAll();
            return new ResponseEntity(productList, HttpStatus.OK);
        }catch (NoConnectedToMQException nce) {
            log.error(nce.getMessage(), nce.getCause());
            return new ResponseEntity(nce.getMessage(), nce.getStatus());
        }
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO) {
        produceProductModule.addProduct(productDTO);
        templateMsg.convertAndSend("/topic/products", productDTO);
    }

    @Override
    public ResponseEntity getByParam(Integer page, Integer size, String title, BigDecimal min, BigDecimal max) {
        String PRODUCT_URL= "http://localhost:8083/api/v1/product";
        try{
            ResponseEntity<PageableResponse<ProductDTO>> exchangeProductList = restTemplate//вынести в service
                    .exchange(PRODUCT_URL + "/list?page="+page  +"&size="+size  +"&titleFilter="+title  +"&min="+min+  "&max="+max,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<PageableResponse<ProductDTO>>(){});
            if (exchangeProductList.getBody()!=null) {
                return new ResponseEntity(exchangeProductList, HttpStatus.OK);
            }
        }catch (RuntimeException ce){
            ce.printStackTrace();
            return new ResponseEntity("Невозможно получить данные.", HttpStatus.NO_CONTENT);
        }
        return   new ResponseEntity("Нет ответа", HttpStatus.NO_CONTENT);


    }
}
