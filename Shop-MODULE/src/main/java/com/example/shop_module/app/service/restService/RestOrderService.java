package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.restClient.HttpRestClient;
import com.example.shop_module.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestOrderService implements OrderService {

    private final HttpRestClient restClient;

    public RestOrderService(HttpRestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ResponseEntity getOrderByMail(String mail) {
        String url = "/orders?mail=" + mail;
        try{
            ResponseEntity response =  restClient.exchangeForEntity(url, null, new ParameterizedTypeReference<List<OrderDTO>>() {});
            return response;
        }catch (NoConnectedToRestService e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }

    }

    @Override
    public ResponseEntity createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void cancelOrder(Long id) {

    }

    @Override
    public ResponseEntity getOrderById(Long id) {
        String url = "/order/get/byId/" + id;
        try{
            ResponseEntity response = restClient.getForEntity(url, OrderDTO.class);
            return response;
        }catch (NoConnectedToRestService e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @Override
    public ResponseEntity updateOrder(OrderDTO orderDTO) {
        return null;
    }
}
