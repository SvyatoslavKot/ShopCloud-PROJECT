package com.example.shop_module.app.restClient;

import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.restClient.abstraction.HttpClientAbstractRequester;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpClientRequester extends HttpClientAbstractRequester {

    private final String REQUEST_HOST;
    private final String REQUEST_PORT;
    private final String API_VERSION;

    public HttpClientRequester(RestTemplate restTemplate, String host, String port, String api_version) {
        super(restTemplate);
        this.REQUEST_HOST = host;
        this.REQUEST_PORT = port;
        this.API_VERSION = api_version;
    }

    @Override
    public ResponseEntity<?> responseForEntity(String url, Class<?> responseType) {
        String URL =  REQUEST_HOST + REQUEST_PORT + API_VERSION + url;
        System.out.println(URL);
        try{
            ResponseEntity<?> response = requester.getForEntity(URL, responseType);
            return response;
        }catch (Exception e) {
            throw new NoConnectedToRestService();
        }
    }

    @Override
    public ResponseEntity exchangeForEntity(String url, RequestEntity requestEntity, ParameterizedTypeReference<?> parameterizedTypeReference) {
        String URL = REQUEST_HOST + REQUEST_PORT + API_VERSION + url;
        System.out.println(URL);
        try{
            ResponseEntity response = requester.exchange(URL,
                    HttpMethod.GET,
                    requestEntity,
                    parameterizedTypeReference);
            return response;
        }catch (Exception e) {
            throw new NoConnectedToRestService();
        }
    }
    @Override
    public ResponseEntity exchangePostForEntity(String url, HttpEntity requestEntity, ParameterizedTypeReference<?> parameterizedTypeReference) {
        String URL = REQUEST_HOST + REQUEST_PORT + API_VERSION + url;
        System.out.println(URL);
        try{
            ResponseEntity response = requester.exchange(URL,
                    HttpMethod.POST,
                    requestEntity,
                    parameterizedTypeReference);
            return response;
        }catch (Exception e) {
            throw new NoConnectedToRestService();
        }
    }

    @Override
    public ResponseEntity postForEntity(String url, Object requestObject, Class<?> responseType) {
        String URL =  REQUEST_HOST + REQUEST_PORT + API_VERSION + url;
        try{
            ResponseEntity response = requester.postForEntity(URL,requestObject, responseType);
            return response;
        }catch (Exception e) {
            throw new NoConnectedToRestService();
        }
    }
}
