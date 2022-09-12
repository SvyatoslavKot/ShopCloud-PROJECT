package com.example.eurekaclient2.controller;

import com.example.eurekaclient2.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/user/v1/")
public class UserAddController {
/*


   // private UserReposiroty userReposiroty;
  //  @Autowired
  //  public UserAddController(UserReposiroty userReposiroty) {
        this.userReposiroty = userReposiroty;
    }

    @GetMapping("user/name/{name}")
    public Optional<User> getUserByName(@PathVariable("name") String name){
        return userReposiroty.findByName(name);
    }

    @GetMapping("user/list")
    public Iterable<User> getAllUsers(){
        return userReposiroty.findAll();
    }*/
}