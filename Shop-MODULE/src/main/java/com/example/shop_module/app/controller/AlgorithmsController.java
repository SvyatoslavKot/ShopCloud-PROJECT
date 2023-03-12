package com.example.shop_module.app.controller;

import com.example.shop_module.app.service.ServiceFactory;
import com.example.shop_module.app.service.abstraction.AlgorithmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/algorithms")
public class AlgorithmsController {

    private final AlgorithmsService algorithmsService;

    public AlgorithmsController(@Qualifier("myKafkaTemplate")KafkaTemplate kafkaTemplate) {
        this.algorithmsService = ServiceFactory.newAlgorithmsService(kafkaTemplate);
    }

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
