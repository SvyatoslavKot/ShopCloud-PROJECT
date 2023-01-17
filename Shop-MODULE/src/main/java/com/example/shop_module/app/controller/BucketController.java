package com.example.shop_module.app.controller;

import com.example.shop_module.app.dto.OrderDTO;
import com.example.shop_module.app.dto.BucketDTO;
import com.example.shop_module.app.service.BucketService;
import com.example.shop_module.app.service.OrderService;
import com.example.shop_module.app.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@Slf4j
public class BucketController {
    private final BucketService rabbitBucketService;
    private final ProductService rabbitProductService;
    private final OrderService rabbitOrderService;
    private final BucketService restBucketService;

    public BucketController(@Qualifier("rabbitBucketService") BucketService rabbitBucketService,
                            @Qualifier("rabbitProductService") ProductService rabbitProductService,
                            @Qualifier("rabbitOrderService") OrderService rabbitOrderService,
                            @Qualifier("restBucketService") BucketService restBucketService) {
        this.rabbitBucketService = rabbitBucketService;
        this.rabbitProductService = rabbitProductService;
        this.rabbitOrderService = rabbitOrderService;
        this.restBucketService = restBucketService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if (principal == null ){
            model.addAttribute("bucket", new BucketDTO());
        }
        else {
            ResponseEntity response = rabbitBucketService.getBucketByUser(principal.getName());
            BucketDTO bucket = (BucketDTO) response.getBody();
            model.addAttribute("bucket" , bucket);

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
        rabbitProductService.removeFromBucket(id, principal.getName());
        return "redirect:/bucket";
    }

    @GetMapping("/bucket/order_confirm")
    public String orderComfirm(Model model, Principal principal) {
        if (principal != null ) {
             ResponseEntity response = restBucketService.commitBucketToOrder(principal.getName());
             if (response.getStatusCode().equals(HttpStatus.OK)){
                 return "product_page/order_confirm_success";
             }

             String msg = (String) response.getBody();
             model.addAttribute("msg", msg);
            return "redirect:/bucket";
        }
        return "redirect:/bucket";
    }
}
