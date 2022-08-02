package com.example.shop_module.controller;

import com.example.shop_module.domain.Bucket;
import com.example.shop_module.domain.Role;
import com.example.shop_module.domain.User;
import com.example.shop_module.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping({"", "/"})
    public String index(){
        return "main";
    }

    @RequestMapping("/login")
    public String login(){
        System.out.println(userRepository.findByMail("Mail@mail"));
        return "login";
    }
}
