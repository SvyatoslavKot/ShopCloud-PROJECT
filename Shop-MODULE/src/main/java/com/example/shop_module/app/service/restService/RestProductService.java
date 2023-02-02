package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.restClient.RestProductClient;
import com.example.shop_module.app.service.ProductService;
import com.example.shop_module.app.wrapper.PageableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@Slf4j
public class RestProductService implements ProductService {

    private final RestProductClient restProductClient;

    public RestProductService( RestProductClient restProductClient ) {
        this.restProductClient = restProductClient;
    }

    @Override
    public ResponseEntity getAll() {
        return null;
    }

    @Override
    public ResponseEntity getById(Long id) throws ResponseMessageException {
        String url = "/product" + "?id=" + id;

        ResponseEntity responseEntity = null;

        try{
            responseEntity = restProductClient.getForEntity(url,ProductDTO.class);
            System.out.println("response -> " + responseEntity.getStatusCode() + "  " + responseEntity.getBody());
        }catch (NoConnectedToRestService e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            return responseEntity;
        }
        String msg = responseEntity.getHeaders().get("message").stream().findFirst().get();
        System.out.println(msg);
        return new ResponseEntity(msg, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity getByParam(Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<String> title,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max) {
        try{
            Page<ProductDTO> pageProduct = restProductClient.getAllByParam(page, size, title, min, max);
            return new ResponseEntity(pageProduct, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public void addToUserBucket(Long productId, String mail) {
        restProductClient.addProductToBucket(productId, mail);

    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        restProductClient.removeFronBucket(productId,mail);
    }

    @Override
    public void addProduct(ProductDTO productDTO) throws RuntimeException {
            restProductClient.addProduct(productDTO);
    }

    @Override
    public void updateProduct(ProductDTO updateProduct) {

    }
}
