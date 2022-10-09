package com.example.eurikaclient.controller;

import com.example.eurikaclient.jwtBeans.AuthRequestUserDTO;
import com.example.eurikaclient.jwtBeans.UserRequest;
import com.example.eurikaclient.provider.CurrentUserProvider;
import com.example.eurikaclient.provider.JwtTokenProvider;
import com.example.eurikaclient.provider.JwtTokenProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/main")
@AllArgsConstructor
@Slf4j
public class TestController {

    private final CurrentUserProvider currentUserProvider;
    private final JwtTokenProviderService jwtTokenProviderService;

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("user", new AuthRequestUserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login (@ModelAttribute("user") AuthRequestUserDTO authRequestUserDTO,
                         Model model, HttpServletResponse response) {
        if (authRequestUserDTO.getMail()!=null && authRequestUserDTO.getMail().length()>1
        && authRequestUserDTO.getPassword()!=null && authRequestUserDTO.getPassword().length()>1){
            try {
                jwtTokenProviderService.login(response,authRequestUserDTO.getMail(),authRequestUserDTO.getPassword());
                return "redirect:/main/success";
            } catch (Exception e ) {
                //throw new RuntimeException(e.getMessage());
                e.printStackTrace();
                System.out.println(e.getMessage());
                log.error(e.getMessage(),e);
                model.addAttribute("msg", e.getMessage());
                return "login";
            }
        }
        model.addAttribute("msg", "Заполните поля");
        return "login";
    }
    @GetMapping("/tests")
    public String tests(){
        return "test";
    }

    @GetMapping("/success")
    public String success(HttpServletRequest request, Model model){
            model.addAttribute("CurrentUser",currentUserProvider.get(request));
        return "succes";
    }

    @GetMapping("/registration")
    public String registartionView(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registartion (@ModelAttribute("user")UserRequest userRequest,
                                Model model,HttpServletResponse response) {

        userRequest = new UserRequest("new@mail", "name", "lastName", "1111", "NEW");
        try {
            jwtTokenProviderService.registration(response, userRequest);
            return "redirect:/main/success";
        } catch (Exception e) {
            //throw new RuntimeException(e.getMessage());
            e.printStackTrace();
            System.out.println(e.getMessage());
            log.error(e.getMessage(), e);
            model.addAttribute("msg", e.getMessage());
            return "registration";
        }
    }

    @PostMapping("/logout")
    public String logout (HttpServletResponse response, HttpServletRequest request){
        jwtTokenProviderService.logoutUser(response);
        currentUserProvider.clearRequestAttribute(request);
        return "succes";
    }
}
