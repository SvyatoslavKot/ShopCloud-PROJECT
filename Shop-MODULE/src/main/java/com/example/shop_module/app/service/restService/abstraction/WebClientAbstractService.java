package com.example.shop_module.app.service.restService.abstraction;

import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.restClient.WebClientRequester;
import com.example.shop_module.app.restClient.abstraction.WebClientRequestable;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class WebClientAbstractService {

    public WebClientRequestable requester;

    public WebClientAbstractService(WebClient.Builder webBuilder, HttpClientSettings httpClientSettings) {
        this.requester = new WebClientRequester(webBuilder,
                httpClientSettings.getPRODUCT_HOST(),
                httpClientSettings.getCLIENT_PORT(),
                httpClientSettings.getAPI_VERSION());
    }
}
