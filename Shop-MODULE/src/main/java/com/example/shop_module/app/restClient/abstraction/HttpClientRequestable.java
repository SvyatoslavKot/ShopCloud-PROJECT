package com.example.shop_module.app.restClient.abstraction;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

public interface HttpClientRequestable {


    ResponseEntity responseForEntity(String url, Class<?> responseClass);
    ResponseEntity exchangeForEntity (String url, RequestEntity requestEntity, ParameterizedTypeReference<?> parameterizedTypeReference );
    ResponseEntity exchangePostForEntity(String url, HttpEntity requestEntity, ParameterizedTypeReference<?> parameterizedTypeReference);
    ResponseEntity postForEntity ( String url, Object requestObject, Class<?> responseType );
}
