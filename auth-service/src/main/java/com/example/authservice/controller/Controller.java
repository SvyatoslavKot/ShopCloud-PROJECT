package com.example.authservice.controller;

import com.example.authservice.rest.AuthenticateResrResponseDTO;
import com.example.authservice.rest.AuthenticationRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;


@org.springframework.stereotype.Controller
@RequestMapping("/auth")
public class Controller {

    private RestTemplate restTemplate;
    HttpPost request = new HttpPost("http://localhost:8082/api/v1/auth/login");

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
