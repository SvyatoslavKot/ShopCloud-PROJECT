package com.example.eurikaclient.controller;

import com.example.eurikaclient.model.AuthRequestUserDTO;
import com.example.eurikaclient.model.UserRequest;
import com.example.eurikaclient.model.UserResponse;
import com.example.eurikaclient.provider.CurrentUserProvider;
import com.example.eurikaclient.provider.JwtSettingsProvider;
import com.example.eurikaclient.provider.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
@RequestMapping("/main")
@AllArgsConstructor
@Slf4j
public class TestControler {

    private final JwtTokenProvider jwtTokenProvider;
    private final CurrentUserProvider currentUserProvider;


    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("user", new AuthRequestUserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login (@ModelAttribute("user") AuthRequestUserDTO authRequestUserDTO,
                         Model model, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        if (authRequestUserDTO.getMail()!=null && authRequestUserDTO.getMail().length()>1
        && authRequestUserDTO.getPassword()!=null && authRequestUserDTO.getPassword().length()>1){
            try {
                jwtTokenProvider.login(response,authRequestUserDTO.getMail(),authRequestUserDTO.getPassword());
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
    public String success(HttpServletRequest request){
        System.out.println("success ->" + currentUserProvider.get(request));
        try {
            jwtTokenProvider.getCurrentUserFromToken(jwtTokenProvider.resolveToken(request));
        } catch (JSONException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "succes";
        }
        return "succes";
    }

    @GetMapping("/registration")
    public String registartionView(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registartion (@ModelAttribute("user")UserRequest userRequest,
                                Model model, HttpServletRequest httpServletRequest, HttpServletResponse response) {

        userRequest = new UserRequest("new@mail", "name", "lastName", "1111", "NEW");
        try {
            jwtTokenProvider.registration(response, userRequest);
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
}
