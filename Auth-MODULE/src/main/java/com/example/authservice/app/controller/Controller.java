package com.example.authservice.app.controller;

import com.example.authservice.app.rest.AuthenticateResrResponseDTO;
import com.example.authservice.app.rest.AuthenticationRequestDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@org.springframework.stereotype.Controller
@RequestMapping("/auth")
public class Controller {

    //EXAMPLE

    private RestTemplate restTemplate;
    //HttpPost request = new HttpPost("http://localhost:8082/api/v1/auth/login");

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute(new AuthenticationRequestDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthenticationRequestDTO authUser) throws IOException, JSONException {

        String url = "http://localhost:8082/api/v1/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("mail", "admin@mail");
        personJsonObject.put("password", "admin");

        HttpEntity request = new HttpEntity(personJsonObject.toString(), headers);

        ResponseEntity responseEntity = restTemplate.postForEntity(url, request, AuthenticateResrResponseDTO.class);


        System.out.println(responseEntity.getBody());

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        System.out.println(jsonObject.get("token"));

        return "succes";
    }

    @GetMapping("/success")
    public String success(){
        return "succes";
    }
}
