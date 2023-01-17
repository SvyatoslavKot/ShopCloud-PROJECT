package com.example.shop_module.app.controller;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.service.restService.ReactClientShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/react/client")
public class ReactClientController {

    @Autowired
    private ReactClientShopService clientService;

    @GetMapping("/getByMail/{mail}")
    public String getByMail (@PathVariable("mail") String mail) {
             ResponseEntity<UserDTO>  response = clientService.getByMail(mail);
             return response.getBody().toString();

    }
}
