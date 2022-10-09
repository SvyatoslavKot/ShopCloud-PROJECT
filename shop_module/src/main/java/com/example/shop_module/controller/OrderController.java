package com.example.shop_module.controller;


import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.exceptions.NoConnectedToMQException;
import com.example.shop_module.exceptions.ResponseMessageException;
import com.example.shop_module.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long id){
        orderService.cancelOrder(id);

        return "redirect:/product/list";
    }

    @GetMapping("/{orderId}/userdata")
    public String orderUserData(@PathVariable("orderId") Long id, Model model) {
        ResponseEntity responseOrder = orderService.getOrderById(id);

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
        ResponseEntity response = orderService.updateOrder(orderDTO);
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
        ResponseEntity responseOrder = orderService.getOrderById(orderId);
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
