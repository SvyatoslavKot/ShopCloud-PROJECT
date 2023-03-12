package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.restClient.abstraction.HttpClientRequestable;
import com.example.shop_module.app.service.abstraction.BucketService;
import com.example.shop_module.app.service.restService.abstraction.HttpClientAbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpClientBucketService extends HttpClientAbstractService implements BucketService {

    private HttpClientRequestable requester;
    private String requestUrl;
    private String PRODUCT_CONFIRM = "/product/confir?mail=";

    public HttpClientBucketService(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        super(restTemplate, httpClientSettings);
        this.requester = getRequester();
    }

    @Override
    public ResponseEntity getBucketByUser(String email) {
        return null;
    }

    @Override
    public ResponseEntity commitBucketToOrder(String email) {
        requestUrl = PRODUCT_CONFIRM + email;
        try{
            ResponseEntity response = requester.responseForEntity(requestUrl,Void.class);
            log.info("Resposne from Bucket Service ->  {}", response);

            if (!response.getStatusCode().equals(HttpStatus.OK)){
                String msg = response.getHeaders().get("message").stream().findFirst().get();
                return new ResponseEntity(msg, HttpStatus.SERVICE_UNAVAILABLE);
            }
                return response;

        }catch (NoConnectedToRestService e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }catch (Exception e) {
            return new ResponseEntity( "Нет ответа от Rest Service", HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
}
