package com.example.shop_module.controller;

import com.example.shop_module.aopService.MeasureMethod;
import com.example.shop_module.domain.Bucket;
import com.example.shop_module.domain.Role;
import com.example.shop_module.domain.User;
import com.example.shop_module.repository.UserRepository;
import com.example.shop_module.service.SessionObjectHolder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private final SessionObjectHolder sessionObjectHolder;

    @Autowired
    AmqpTemplate template;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @MeasureMethod
    @RequestMapping({"", "/"})
    public String index(Model model){
        model.addAttribute("amountClick", sessionObjectHolder.getAmountClick());

        System.out.println("Emit to queue1");
        template.convertAndSend("queue1","Message to queue");
        return "main";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
