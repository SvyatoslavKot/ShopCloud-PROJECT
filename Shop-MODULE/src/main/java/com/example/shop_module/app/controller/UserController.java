package com.example.shop_module.app.controller;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.mq.ProduceShopClient;
import com.example.shop_module.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService rabbitUserService;
    private ProduceShopClient producerShopClientService;
    @Autowired
    public UserController(@Qualifier("rabbitUserService") UserService rabbitUserService,
                          @Qualifier("rabbitProduceShopClient") ProduceShopClient producerShopClientService) {
        this.rabbitUserService = rabbitUserService;
        this.producerShopClientService = producerShopClientService;
    }


    @GetMapping("/list")
    public String userList (Model model){
        model.addAttribute("users", rabbitUserService.findAllUserDto());
        return "userList";
    }
    @PreAuthorize("isAuthenticated() and #mail == authentication.principal.username")
    @GetMapping("/{mail}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("mail") String mail) {
        ResponseEntity responseEntity =  rabbitUserService.findByMail(mail);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            UserDTO user = (UserDTO) responseEntity.getBody();
            return user.getRole().name();
        }
        var msg = (String) responseEntity.getBody();
        throw new RuntimeException(msg);
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("dto", new UserDTO());
        return  "registration";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("dto") UserDTO userDTO, Model model) {
        System.out.println(userDTO.getMail() + " " + userDTO.getPassword());
        ResponseEntity response = rabbitUserService.registrationClient(userDTO);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return "redirect:/login";
        }
        System.out.println("error ->" + response.getBody());
        return "redirect:/user/new";
    }


    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if(principal == null) {
            throw new RuntimeException("You are not authorize!");
        }
        ResponseEntity responseEntity =  rabbitUserService.findByMail(principal.getName());
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            UserDTO user = (UserDTO) responseEntity.getBody();
            model.addAttribute("user", user);
            return "profile";
        }
        var msg = (String) responseEntity.getBody();
        throw new RuntimeException(msg);

    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getMail())){
            throw new RuntimeException("You are not authorize!");
        }
        rabbitUserService.updateProfile(dto);
        return "redirect:/user/profile";
    }
}
