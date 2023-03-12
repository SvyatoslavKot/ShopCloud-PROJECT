package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.restClient.abstraction.HttpClientRequestable;
import com.example.shop_module.app.service.abstraction.OrderService;
import com.example.shop_module.app.service.restService.abstraction.HttpClientAbstractService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpClientOrderService extends HttpClientAbstractService implements OrderService {

    private HttpClientRequestable requester;
    private String requestUrl;
    private String GET_ORDERS_BY_MAIL = "/get/orders/bymail?mail=";
    private String GET_ORDER_BY_ID = "/order/get/byId/";

    public HttpClientOrderService(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        super(restTemplate, httpClientSettings);
        this.requester = getRequester();
    }

    @Override
    public ResponseEntity getOrderByMail(String mail) {
        requestUrl = GET_ORDERS_BY_MAIL + mail;

        try{
            return  requester.exchangeForEntity(requestUrl, null, new ParameterizedTypeReference<List<OrderDTO>>() {});
        }catch (NoConnectedToRestService e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }catch (Exception e) {
            return new ResponseEntity( "Нет ответа от Rest Service. \n" + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @Override
    public ResponseEntity createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public ResponseEntity getOrderById(Long id) {
        requestUrl = GET_ORDER_BY_ID + id;
        try{
            return requester.responseForEntity(requestUrl, OrderDTO.class);
        }catch (NoConnectedToRestService e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }catch (Exception e) {
            return new ResponseEntity( "Нет ответа от Rest Service. \n" + e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    public ResponseEntity updateOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void cancelOrder(Long id) {

    }
}
