package com.example.shop_module.app.restClient.abstraction;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class WebClientAbstractRequester implements WebClientRequestable{
    public final WebClient.Builder webBuilder;

    public WebClientAbstractRequester(WebClient.Builder webBuilder) {
        this.webBuilder = webBuilder;
    }

    public WebClient.Builder getWebBuilder() {
        return webBuilder;
    }
}
