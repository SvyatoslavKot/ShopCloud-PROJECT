package com.example.eurikaclient.controller;

import com.example.eurikaclient.model.AuthRequestUserDTO;
import com.example.eurikaclient.model.AuthRequestUserDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/main")
public class TestControler {

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${eureka.instance.instance-id}")
    private String id;

    @GetMapping("/test")
    public String test(){
        return id;
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("user", new AuthRequestUserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login (@ModelAttribute("user") AuthRequestUserDTO authRequestUserDTO,
                         Model model) {

        String url = "http://localhost:8082/api/v1/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();

        if (authRequestUserDTO.getMail()!=null && authRequestUserDTO.getMail().length()>1
        && authRequestUserDTO.getPassword()!=null && authRequestUserDTO.getPassword().length()>1){
            try {
                personJsonObject.put("mail", authRequestUserDTO.getMail());
                personJsonObject.put("password", authRequestUserDTO.getPassword());
                HttpEntity request = new HttpEntity(personJsonObject.toString(), headers);
                ResponseEntity<String> responseEntity  = restTemplate.postForEntity(url, request, String.class);
                JSONObject response = new JSONObject(responseEntity.getBody());
                String token = (String) response.get("token");
                String mail = (String) response.get("email");
                System.out.println("Mail = "+ mail + "  token = " + token);


                return "redirect:/main/success";

            } catch (JSONException e) {
                //throw new RuntimeException(e.getMessage());
                e.printStackTrace();
                System.out.println(e.getMessage());
                model.addAttribute("msg", e.getMessage());
                return "login";
            }
        }
        model.addAttribute("msg", "Заполните поля");
        return "login";
    }

    @GetMapping("/success")
    public String success(){
        return "succes";
    }

}
