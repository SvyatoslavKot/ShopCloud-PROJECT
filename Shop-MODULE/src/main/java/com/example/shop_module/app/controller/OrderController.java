package com.example.shop_module.app.controller;


import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.service.ServiceFactory;
import com.example.shop_module.app.service.abstraction.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService rabbitOrderService;
    private final OrderService restOrderService;

    public OrderController(RabbitTemplate rabbitTemplate,
                           RestTemplate restTemplate,
                           HttpClientSettings httpClientSettings) {
        this.rabbitOrderService = ServiceFactory.newOrderService(rabbitTemplate);
        this.restOrderService = ServiceFactory.newOrderService(restTemplate,httpClientSettings);
    }

    @GetMapping("/all")
    public String getAllOrderByMail(Model model, Principal principal){
        if (principal.getName() != null) {
            ResponseEntity response = restOrderService.getOrderByMail(principal.getName());
            if (response.getStatusCode().equals(HttpStatus.OK)){
                List<OrderDTO> orderDTOS = (List<OrderDTO>) response.getBody();
                model.addAttribute("orders", orderDTOS);
                System.out.println(orderDTOS);
                return "product_page/client_orders";
            }
            return "product_page/client_orders";
        }
        return "product_page/products";
    }

    @GetMapping("/order")
    public String getOrderById(Model model, @RequestParam(name = "id") Long id) {
        ResponseEntity response = restOrderService.getOrderById(id);
        if (response.getStatusCode().equals(HttpStatus.OK)){
            OrderDTO order = (OrderDTO) response.getBody();
            model.addAttribute("order", order);
        }else {
            String msg = null;
               msg = response.getHeaders().get("message").stream().findFirst().get();
               if (msg == null) {
                   msg = (String) response.getBody();
               }
            model.addAttribute("msg", msg);

        }
        return  "product_page/order";
    }

    @GetMapping("{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long id){
        rabbitOrderService.cancelOrder(id);

        return "redirect:/product/list";
    }

    @GetMapping("/{orderId}/userdata")
    public String orderUserData(@PathVariable("orderId") Long id, Model model) {
        ResponseEntity responseOrder = rabbitOrderService.getOrderById(id);

        if (responseOrder.getStatusCode().equals(HttpStatus.OK)){
            OrderDTO order = (OrderDTO) responseOrder.getBody();
            model.addAttribute("order", order);
            return "product_page/order-user-data";

        }else{
            String msg = (String) responseOrder.getBody();
            model.addAttribute("msg", msg);
            return "product_page/order-user-data";
        }
    }

    @PostMapping("/{orderId}/userdata")
    public String updateOrderUserData(@PathVariable("orderId") Long id, @ModelAttribute("order") OrderDTO orderDTO, Model model ) {
        if (orderDTO.getAddress().isEmpty() || orderDTO.getAddress().length() < 1){
            model.addAttribute("msg", "заполните поле 'аддрес'");
            model.addAttribute("order", orderDTO);
            return "product_page/order-user-data";
        }
        if (orderDTO.getPhone().isEmpty() || orderDTO.getPhone().length() < 1){
            model.addAttribute("msg", "заполните поле 'Телефон'");
            model.addAttribute("order", orderDTO);
            return "product_page/order-user-data";
        }
        if (orderDTO.getDelivery().isEmpty() || orderDTO.getDelivery().length() < 1){
            model.addAttribute("msg", "заполните поле 'Способ доставки'");
            model.addAttribute("order", orderDTO);
            return "product_page/order-user-data";
        }
        orderDTO.setId(id);
        ResponseEntity response = rabbitOrderService.updateOrder(orderDTO);
        if (response.getStatusCode().equals(HttpStatus.OK)){
            System.out.println("Controller orderDto -> " + orderDTO);
            return "redirect:/order/{orderId}/orderPaying";
        }
            var msgStr = (String) response.getBody();
            model.addAttribute("msg", msgStr.split("\"")[1]);
            model.addAttribute("order", orderDTO);
            return "product_page/order-user-data";
    }
    @GetMapping("/{orderId}/orderPaying")
    public String orderPaying(@PathVariable("orderId") Long orderId, Model model){
        ResponseEntity responseOrder = rabbitOrderService.getOrderById(orderId);
        if (responseOrder.getStatusCode().equals(HttpStatus.OK)){
            OrderDTO order = (OrderDTO) responseOrder.getBody();
            model.addAttribute("order", order);
            return "product_page/order-paying";

        }else{
            String msg = (String) responseOrder.getBody();
            model.addAttribute("msg", msg);
            return "product_page/order-paying";
        }
    }
}
