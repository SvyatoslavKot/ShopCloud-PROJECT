package com.example.order_module.controller;

import com.example.order_module.dto.OrderDTO;
import com.example.order_module.service.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders" , produces ="application/json")
    public ResponseEntity getOrderByMail (@RequestParam(name = "mail") String mail){
        System.out.println("mail -> " + mail);
        List<OrderDTO> orderList = orderService.getOrdersByMail(mail);
        System.out.println("response -> " + orderList);
        return new ResponseEntity(orderList, HttpStatus.OK);
    }

    @GetMapping(value = "/order/get/byId/{id}")
    public ResponseEntity getOrderById (@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        try{
            OrderDTO orderDTO = orderService.getOrderById(id);
            return new ResponseEntity(orderDTO, HttpStatus.OK);
        }catch (Exception e) {
            headers.set("message", e.getMessage());
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        }

    }
}
