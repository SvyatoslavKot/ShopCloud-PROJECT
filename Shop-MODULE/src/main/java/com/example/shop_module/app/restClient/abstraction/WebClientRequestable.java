package com.example.shop_module.app.restClient.abstraction;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface WebClientRequestable {

    Mono<ResponseEntity> performRequest(String url, Class<?> responseType);
}
