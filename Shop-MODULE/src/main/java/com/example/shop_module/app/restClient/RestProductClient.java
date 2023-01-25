package com.example.shop_module.app.restClient;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.wrapper.PageableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class RestProductClient {
    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private final HttpRestClient httpClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${product-module.host}")
    private String PRODUCT_HOST;
    @Value("${product-module.port}")
    private String PRODUCT_PORT;

    public RestProductClient(RestTemplate restTemplate, HttpRestClient httpClient) {
        this.restTemplate = restTemplate;
        this.httpClient = httpClient;
    }

    public ResponseEntity getForEntity ( String url, Class<?> responseType ) {
        String URL =  PRODUCT_HOST + PRODUCT_PORT + "/api/v1" + url;
        try{
            ResponseEntity response = restTemplate.getForEntity(URL, responseType);
            log.info("Response from product Rest Service -> {}", response);
            return response;
        }catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
            throw new NoConnectedToRestService();
        }

    }

    public ResponseEntity postForEntity ( String url, Object requestObject, Class<?> responseType ) {
        String URL =  PRODUCT_HOST + PRODUCT_PORT + "/api/v1" + url;
        ResponseEntity response = restTemplate.postForEntity(URL,requestObject, responseType);
        System.out.println( response);
        return response;
    }

    public ResponseEntity postForEntity ( String url, HttpEntity requestEntity, Class<?> responseType ) {
        String URL =  PRODUCT_HOST + PRODUCT_PORT + "/api/v1" + url;
        ResponseEntity response = restTemplate.postForEntity(URL,requestEntity, responseType);
        System.out.println( response);
        return response;
    }

    public ResponseEntity exchangeForEntity (String url, RequestEntity requestEntity, ParameterizedTypeReference<?> parameterizedTypeReference ) {
        String URL = PRODUCT_HOST + PRODUCT_PORT + "/api/v1" + url;
        ResponseEntity response = restTemplate.exchange(URL,
                HttpMethod.GET,
                requestEntity,
                parameterizedTypeReference);
        return response;
    }

    public ProductDTO getById(Long id) throws ResponseMessageException {
        String PRODUCT_URL = PRODUCT_HOST + PRODUCT_PORT + "/api/v1/product";
        ProductDTO product = null;
        ResponseEntity<ProductDTO> responseEntity = null;

        try{
            responseEntity = restTemplate.getForEntity(
                    PRODUCT_URL + "?id=" + id, ProductDTO.class);
            System.out.println("response -> " + responseEntity.getStatusCode() + "  " + responseEntity.getBody());
        }catch (RuntimeException e) {
            String msg = "Something wrong! ";
            log.error(msg + e.getMessage());
            throw  new NoConnectedToGRpsServer();
        }

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            product = responseEntity.getBody();
            return product;
        }
        String msg = responseEntity.getHeaders().get("message").stream().findFirst().get();
        System.out.println(msg);
        throw new ResponseMessageException(responseEntity.getStatusCode(), msg);
    }

    public void addProduct (ProductDTO productDTO) {
        String PRODUCT_URL = PRODUCT_HOST + PRODUCT_PORT + "/api/v1/product/add";
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(PRODUCT_URL,productDTO, String.class);
            log.info("response -> " + response);
        } catch (RuntimeException e) {
            throw new NoConnectedToGRpsServer();
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)){
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);
        }
    }

    public Page<ProductDTO> getAllByParam (Optional<Integer> page,
                                           Optional<Integer> size,
                                           Optional<String> title,
                                           Optional<BigDecimal> min,
                                           Optional<BigDecimal> max) {
        String PRODUCT_URL = PRODUCT_HOST + PRODUCT_PORT + "/api/v1/product";
        ResponseEntity<PageableResponse<ProductDTO>> exchangeProductList = null;

        try{
            exchangeProductList = restTemplate
                    .exchange(PRODUCT_URL + "/list?page="+page.orElse(1)
                                    +"&size="+size.orElse(4)
                                    +"&titleFilter="+title.orElse("")
                                    +"&min="+min.orElse(new BigDecimal(1))
                                    +"&max="+max.orElse(new BigDecimal(999999 * 9)),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<PageableResponse<ProductDTO>>(){});
            log.info("response -> " + exchangeProductList);

        }catch (RuntimeException ce){
            ce.printStackTrace();
            throw new NoConnectedToGRpsServer();
        }

        if (exchangeProductList.getStatusCode().equals(HttpStatus.OK)) {
            return exchangeProductList.getBody();
        }

        String msg = exchangeProductList.getHeaders().get("message").stream().findFirst().get();
        throw new ResponseMessageException(exchangeProductList.getStatusCode(), msg);
    }

    public void addProductToBucket (Long productId, String mail) {
        String PRODUCT_URL = PRODUCT_HOST + PRODUCT_PORT + "/api/v1/product/addToBucket/" + productId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("USER_MAIL", mail);
        ResponseEntity<Void> response = null;
        HttpEntity requestEntity = new HttpEntity(headers);
        try{
            response = restTemplate.postForEntity(PRODUCT_URL,requestEntity, Void.class);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new NoConnectedToGRpsServer();
        }

        if (response.getStatusCode().equals(HttpStatus.OK)){
            log.info("product add");
        } else {
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);
        }
    }

    public void removeFronBucket(Long productId, String mail) {
        String PRODUCT_URL = PRODUCT_HOST + PRODUCT_PORT + "/api/v1/product/removeFromBucket";
        HttpHeaders headers = new HttpHeaders();
        headers.set("USER_MAIL", mail);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Void> response = null;
        try{
            response = restTemplate.postForEntity(PRODUCT_URL+"?id="+productId ,requestEntity, Void.class);
        }catch (RuntimeException e) {
           log.error(e.getMessage());
           throw new NoConnectedToGRpsServer();
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);
        }
    }


    public  void addProductFromFile (String name, MultipartFile file) {
        String URL = PRODUCT_HOST + PRODUCT_PORT + "/api/v1/product/add/fromFile";
        try {
            byte[] bytes = file.getBytes();
            byte[] bName = name.getBytes(StandardCharsets.UTF_8);
            Map<String, byte[]> requestMap = new HashMap<>();
            requestMap.put("name", bName);
            requestMap.put("file" , bytes);
            HttpEntity requestEntity = new HttpEntity<>(requestMap);
            //httpClient.postForEntity(URL,requestEntity,Void.class);
            restTemplate.exchange(URL, HttpMethod.POST,requestEntity,Void.class);
        } catch (IOException e) {
        e.printStackTrace();
    }
       //
    }


}
