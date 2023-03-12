package com.example.shop_module.app.controller;

import com.example.shop_module.app.aopService.MeasureMethod;
import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.security.JwtTokenProviderService;
import com.example.shop_module.app.util.SessionObjectHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final AmqpTemplate template;

    @MeasureMethod
    @RequestMapping( "/main")
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
            ResponseEntity responseEntity = jwtTokenProviderService.login(response,authRequestUserDTO.getMail(),authRequestUserDTO.getPassword());

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                return "redirect:/main";
            }
            String msg = (String) responseEntity.getBody();
            msg = msg.split("\"")[1];
            model.addAttribute("msg", msg);
            return "login";
        }
        model.addAttribute("msg", "Заполните поля");
        return "login";
    }

}
