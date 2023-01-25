package com.example.shop_module.app.restClient;

import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Component
@Slf4j
public class HttpRestClient {

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${order-module.host}")
    private String ORDER_HOST;
    @Value("${order-module.port}")
    private String ORDER_PORT;
    @Value("${order-api-version}")
    private String API_VERSION;

    public HttpRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity exchangeForEntity (String url, RequestEntity requestEntity, ParameterizedTypeReference<?> parameterizedTypeReference ) {
        String URL = ORDER_HOST + ORDER_PORT + API_VERSION + url;
        System.out.println(URL);
        try{
            ResponseEntity response = restTemplate.exchange(URL,
                    HttpMethod.GET,
                    requestEntity,
                    parameterizedTypeReference);
            log.info("Response -> {}", response);

        return response;
        }catch (Exception e) {
            throw new NoConnectedToRestService();
        }
    }
    public ResponseEntity getForEntity ( String url, Class<?> responseType ) {
        String URL =  ORDER_HOST + ORDER_PORT + API_VERSION + url;
        System.out.println(URL);
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
        String URL =  ORDER_HOST + ORDER_PORT + API_VERSION + url;
        try{
            ResponseEntity response = restTemplate.postForEntity(URL,requestObject, responseType);
            return response;
        }catch (Exception e) {
            throw new NoConnectedToRestService();
        }
    }
}
