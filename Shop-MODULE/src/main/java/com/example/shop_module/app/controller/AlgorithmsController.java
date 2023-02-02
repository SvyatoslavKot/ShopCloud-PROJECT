package com.example.shop_module.app.controller;

import com.example.shop_module.app.service.AlgorithmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/algorithms")
public class AlgorithmsController {

    @Autowired
    AlgorithmsService algorithmsService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;


    @GetMapping("/sort/bubble")
    public String bubbleSort(HttpServletRequest request, Model model) {
        System.out.println(request.getSession().getId());
        model.addAttribute("reqID" , request.getSession().getId() );
        return "algorithms/sort_bubble";
    }

    @GetMapping("/sort/quick")
    public String quickSort(HttpServletRequest request, Model model) {
        System.out.println(request.getSession().getId());
        model.addAttribute("reqID" , request.getSession().getId() );
        return "algorithms/sort_quick";
    }

}
