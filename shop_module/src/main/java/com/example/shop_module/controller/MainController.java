package com.example.shop_module.controller;

import com.example.shop_module.aopService.MeasureMethod;
import com.example.shop_module.domain.Bucket;
import com.example.shop_module.domain.Role;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.exceptions.LoginClientException;
import com.example.shop_module.mq.ProduserAuthModule;
import com.example.shop_module.repository.UserRepository;
import com.example.shop_module.security.JwtTokenProviderService;
import com.example.shop_module.service.SessionObjectHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@AllArgsConstructor
public class MainController {

    private final JwtTokenProviderService jwtTokenProviderService;
    private final SessionObjectHolder sessionObjectHolder;
    private final UserRepository userRepository;
    private final ProduserAuthModule produserAuthModule;
    private final AmqpTemplate template;

    @MeasureMethod
    @RequestMapping({ "/main"})
    public String index(Model model){
        model.addAttribute("amountClick", sessionObjectHolder.getAmountClick());
        template.convertAndSend("queue1","Message to queue");
        return "main";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("user", new UserDTO());
        return "login";
    }
    @PostMapping("/login")
    public String login (@ModelAttribute("user") UserDTO authRequestUserDTO,
                         Model model, HttpServletResponse response) {

        if (authRequestUserDTO.getMail()!=null && authRequestUserDTO.getMail().length()>1
                && authRequestUserDTO.getPassword()!=null && authRequestUserDTO.getPassword().length()>1){
            try {
                jwtTokenProviderService.login(response,authRequestUserDTO.getMail(),authRequestUserDTO.getPassword());
                return "redirect:/main";
            } catch (LoginClientException le) {
                System.out.println(le.getMessage());
                String msg = le.getMessage().split("\"")[1];
                model.addAttribute("msg", msg);
                return "login";
            }
        }
        model.addAttribute("msg", "Заполните поля");
        return "login";
    }

}
