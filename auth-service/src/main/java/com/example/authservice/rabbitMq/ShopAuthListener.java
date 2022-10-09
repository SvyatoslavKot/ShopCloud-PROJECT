package com.example.authservice.rabbitMq;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.requestBeans.UserRequest;
import com.example.authservice.security.JwtTokenProvader;
import com.example.authservice.service.AppUserService;
import com.example.authservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class ShopAuthListener {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtTokenProvader jwtTokenProvader;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @RabbitListener(queues = "routingKey-auth_login")
    @Transactional
    public Map<Object, Object> consume2(@Payload HashMap<String, String> request ) {
            try{
                User user = userService.findByMail(request.get("mail"));
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.get("mail"), request.get("password")));

                String token = jwtTokenProvader.createToken(request.get("mail"), user.getRole().name());
                Map<Object, Object> map = new HashMap<>();
                map.put(HttpStatus.OK, token);
                return map;
            }catch (UsernameNotFoundException e) {
                Map<Object, Object> map = new HashMap<>();
                map.put(HttpStatus.NON_AUTHORITATIVE_INFORMATION, e.getMessage());
                return map;
            }catch (AuthenticationException e){
                Map<Object, Object> map = new HashMap<>();
                map.put(HttpStatus.NON_AUTHORITATIVE_INFORMATION, e.getMessage() );
                return  map;
            }
    }


}