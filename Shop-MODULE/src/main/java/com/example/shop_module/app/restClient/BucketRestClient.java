package com.example.shop_module.app.restClient;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@Service
@Slf4j
public class BucketRestClient {
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${product-module.host}")
    private String PRODUCT_HOST;
    @Value("${product-module.port}")
    private String PRODUCT_PORT;

    private final String API_PRODUCT = "/api/v1/bucket";

    public BucketRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   public ResponseEntity getForEntity ( String url, Class<?> responseType) {
        String URL = PRODUCT_HOST + PRODUCT_PORT + API_PRODUCT + url;
        try{
            ResponseEntity response = restTemplate.getForEntity(URL, responseType);
            log.info("Resposne from Bucket Service ->  {}", response);
            return response;
        }catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new NoConnectedToRestService();
        }

   }


    public ResponseEntity confirmOrderFromBucket (String mail) {
        String PRODUCT_URL = PRODUCT_HOST + PRODUCT_PORT + API_PRODUCT +  "/confirm" + "?mail=" + mail;
        ResponseEntity response = null;
        try {
            response = restTemplate.getForEntity(PRODUCT_URL, null);

            log.info("response -> " + response);

        } catch (RuntimeException e) {
            throw new NoConnectedToGRpsServer();
        }

        if (!response.getStatusCode().equals(HttpStatus.OK)){
            String msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);
        }

        return response;
    }
}
