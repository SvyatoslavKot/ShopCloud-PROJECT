package com.example.shop_module.controller;

import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/list")
    public String userList (Model model){
        model.addAttribute("users", userService.findAllUserDto());
        return "userList";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("dto", new UserDTO());
        return  "registration";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("dto") UserDTO userDTO, Model model){
        System.out.println(userDTO.getEmail()+ " " + userDTO.getPassword());


        if (userService.save(userDTO)){
            return "redirect:/login";
        }else {
            model.addAttribute("user", userDTO);
            return "registration";
        }
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if(principal == null) {
            throw new RuntimeException("Yuo are not authorize!");
        }
        User user = userService.finByMail(principal.getName());

        UserDTO dto  = UserDTO.builder()
                .username(user.getName())
                .email(user.getMail())
                .build();
        model.addAttribute("user", dto);
        return "profile";

    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getEmail())){
            throw new RuntimeException("You are not authorize!");
        }
        if(dto.getPassword() !=null
        && !dto.getPassword().isEmpty()
        && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())){
            model.addAttribute("user", dto);
            return "profile";
        }
        userService.updateProfile(dto);
        return "redirect:/user/profile";
    }
}
