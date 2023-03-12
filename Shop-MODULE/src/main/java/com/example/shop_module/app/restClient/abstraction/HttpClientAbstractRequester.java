package com.example.shop_module.app.restClient.abstraction;

import org.springframework.web.client.RestTemplate;

public abstract class HttpClientAbstractRequester implements HttpClientRequestable {
    public RestTemplate requester;

    public HttpClientAbstractRequester(RestTemplate restTemplate) {
        this.requester = restTemplate;
    }

    public RestTemplate getRequester() {
        return requester;
    }
}
