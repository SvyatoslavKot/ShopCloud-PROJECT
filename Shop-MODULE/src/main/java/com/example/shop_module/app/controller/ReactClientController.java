package com.example.shop_module.app.controller;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.service.ServiceFactory;
import com.example.shop_module.app.service.abstraction.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/react/client")
public class ReactClientController {

    private UserService userService;

    public ReactClientController(WebClient.Builder webBuilder, HttpClientSettings httpClientSettings) {
        this.userService = ServiceFactory.newUserService(webBuilder, httpClientSettings);
    }

    @GetMapping("/getByMail/{mail}")
    public String getByMail (@PathVariable("mail") String mail) {
             ResponseEntity<UserDTO>  response = userService.findByMail(mail);
             return response.getBody().toString();

    }
}
