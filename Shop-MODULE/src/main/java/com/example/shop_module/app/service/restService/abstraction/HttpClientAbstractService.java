package com.example.shop_module.app.service.restService.abstraction;

import com.example.shop_module.app.restClient.HttpClientRequester;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.restClient.abstraction.HttpClientRequestable;
import org.springframework.web.client.RestTemplate;

public abstract class HttpClientAbstractService {
    private HttpClientRequestable requester;

    public HttpClientAbstractService(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        this.requester = new HttpClientRequester(restTemplate,
                httpClientSettings.getPRODUCT_HOST(),
                httpClientSettings.getPRODUCT_PORT(),
                httpClientSettings.getAPI_VERSION());
    }

    public HttpClientRequestable getRequester() {
        return requester;
    }

}
