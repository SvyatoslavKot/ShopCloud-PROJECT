package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.restClient.abstraction.HttpClientRequestable;
import com.example.shop_module.app.service.abstraction.ProductService;
import com.example.shop_module.app.service.restService.abstraction.HttpClientAbstractService;
import com.example.shop_module.app.wrapper.PageableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class HttpClientProductService extends HttpClientAbstractService implements ProductService {

    private HttpClientRequestable requester;
    private String requestUrl;

    private String GET_BY_ID = "/product?id=";
    private String GET_BY_PARAM = "/product/list?";
    private String ADD_TO_BUCKET = "/product/addToBucket/";
    private String REMOVE_FROM_BUCKET = "/product/removeFromBucket?id=";
    private String PRODUCT_ADD = "/product/add";
    private String ADD_FROM_FILE = "/product/add/fromFile";



    public HttpClientProductService(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        super(restTemplate, httpClientSettings);
        this.requester = getRequester();
    }

    @Override
    public ResponseEntity getAll() {
        return null;
    }

    @Override
    public ResponseEntity getById(Long id) throws ResponseMessageException {
        requestUrl = GET_BY_ID + id;

        ResponseEntity responseEntity = null;

        try{
            responseEntity = requester.responseForEntity(requestUrl,ProductDTO.class);
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
    public ResponseEntity getByParam(Optional<Integer> page, Optional<Integer> size, Optional<String> title, Optional<BigDecimal> min, Optional<BigDecimal> max) {
        requestUrl = GET_BY_PARAM
                +"page=" +page.orElse(1)
                +"&size="+size.orElse(4)
                +"&titleFilter="+title.orElse("")
                +"&min="+min.orElse(new BigDecimal(1))
                +"&max="+max.orElse(new BigDecimal(999999 * 9));

        ResponseEntity<PageableResponse<ProductDTO>> exchangeProductList = null;

        try{
            exchangeProductList = requester.exchangeForEntity(
                    requestUrl,
                    null,
                    new ParameterizedTypeReference<PageableResponse<ProductDTO>>(){});

        }catch (NoConnectedToRestService e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }catch (Exception e) {
            return new ResponseEntity( "Нет ответа от Rest Service. \n" + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }


        if (exchangeProductList.getStatusCode().equals(HttpStatus.OK)) {
            Page<ProductDTO> pageProduct = exchangeProductList.getBody();
            return new ResponseEntity(pageProduct, HttpStatus.OK);
        }
        String msg = exchangeProductList.getHeaders().get("message").stream().findFirst().get();
        throw new ResponseMessageException(exchangeProductList.getStatusCode(), msg);

    }

    @Override
    public void addToUserBucket(Long productId, String mail) {

        requestUrl = ADD_TO_BUCKET + productId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("USER_MAIL", mail);
        ResponseEntity<Void> response = null;
        HttpEntity requestEntity = new HttpEntity(headers);
        try{
            response = requester.postForEntity(requestUrl, requestEntity, Void.class);
        }catch (NoConnectedToRestService e) {
            log.info(e.getMessage());
        }catch (Exception e) {
            log.info("Нет ответа от Rest Service. \n" + e.getMessage());
        }

        if (response.getStatusCode().equals(HttpStatus.OK)){
            log.info("product add");
        } else {
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);
        }
    }

    @Override
    public void removeFromBucket(Long productId, String mail) {
        requestUrl = REMOVE_FROM_BUCKET + productId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("USER_MAIL", mail);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Void> response = null;
        try{
            response = requester.postForEntity(requestUrl ,requestEntity, Void.class);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new NoConnectedToGRpsServer();
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);
        }
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        requestUrl = PRODUCT_ADD;
        ResponseEntity<String> response = null;
        try {
            response = requester.postForEntity(requestUrl,productDTO, String.class);
            log.info("response -> " + response);
        } catch (RuntimeException e) {
            throw new NoConnectedToGRpsServer();
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)){
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);
        }
    }

    @Override
    public void updateProduct(ProductDTO updateProduct) {

    }

    public  void addProductFromFile (String name, MultipartFile file) {
        requestUrl = ADD_FROM_FILE;
        try {
            byte[] bytes = file.getBytes();
            byte[] bName = name.getBytes(StandardCharsets.UTF_8);
            Map<String, byte[]> requestMap = new HashMap<>();
            requestMap.put("name", bName);
            requestMap.put("file" , bytes);
            HttpEntity requestEntity = new HttpEntity<>(requestMap);
            //ResponseEntity<Void> response = null;
            //httpClient.postForEntity(URL,requestEntity,Void.class);
            //requester.postForEntity(url,requestMap,Void.class);
            requester.exchangePostForEntity(requestUrl, requestEntity,new ParameterizedTypeReference<PageableResponse<Void>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
