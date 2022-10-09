package com.example.shop_module.controller;

import com.example.shop_module.dto.BucketDTO;
import com.example.shop_module.dto.OrderDTO;
import com.example.shop_module.service.BucketService;
import com.example.shop_module.service.OrderService;
import com.example.shop_module.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
@Slf4j
public class BucketController {
    private final BucketService bucketService;
    private final ProductService productService;
    private final OrderService orderService;


    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if (principal == null ){
            model.addAttribute("bucket", new BucketDTO());
        }
        else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket" , bucketDTO);
        }
        return "product_page/bucket";
    }

    @PostMapping("/bucket")
    public String commitBucket(Principal principal) throws Exception {
        if (principal != null) {
            return "redirect:/bucket/order_confirm";

        }
        return "redirect:/bucket";
    }

    @GetMapping("bucket/remove/product{id}")
    public String removeFromBucket(@PathVariable Long id, Principal principal){
        if (principal == null){
            return "redirect:/bucket";
        }
        productService.removeFromBucket(id, principal.getName());
        return "redirect:/bucket";
    }

    @GetMapping("/bucket/order_confirm")
    public String orderComfirm(Model model, Principal principal) {
        if (principal != null ){
            ResponseEntity response = bucketService.commitBucketToOrder(principal.getName());
            if (response.getStatusCode().equals(HttpStatus.OK)){
                OrderDTO orderDTO = (OrderDTO) response.getBody();

                ResponseEntity responseOrder = orderService.createOrder(orderDTO);
                if (responseOrder.getStatusCode().equals(HttpStatus.OK)){
                    orderDTO.setId((Long) responseOrder.getBody());
                    model.addAttribute("order", orderDTO);
                    orderService.getOrderById((Long) responseOrder.getBody());
                    return "product_page/confirmOrder";
                }else {
                    String exceptionMsg = responseOrder.getBody().toString().split("\"")[1];
                    model.addAttribute("msg" , exceptionMsg);
                    log.error(response.getBody().toString());
                    return "product_page/confirmOrder";
                }
            }else {
                String exceptionMsg = response.getBody().toString().split("\"")[1];
                model.addAttribute("msg" , exceptionMsg);
                log.error(response.getBody().toString());
                return "product_page/confirmOrder";
            }
        }
        return "redirect:/bucket";
    }
}
