package com.example.shop_module.app.security;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.service.abstraction.AuthService;
import com.example.shop_module.app.service.ServiceFactory;
import com.example.shop_module.app.service.abstraction.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class JwtTokenProviderService {

    private final JwtSettingsProvider jwtSettingsProvider;
    private final AuthService rabbitAuthService;
    private final UserService rabbitUserService;

    public JwtTokenProviderService(
            RabbitTemplate rabbitTemplate,
            JwtSettingsProvider jwtSettingsProvider) {
        this.jwtSettingsProvider = jwtSettingsProvider;
        this.rabbitAuthService = ServiceFactory.newAuthService(rabbitTemplate);
        this.rabbitUserService = ServiceFactory.newUserService(rabbitTemplate);
    }

    public ResponseEntity login  (HttpServletResponse response, String email, String password){
        try{
            ResponseEntity responseEntity = rabbitAuthService.authorization(email,password);
            Cookie cookie = createCookie((String)responseEntity.getBody());
            response.addCookie(cookie);
            return new ResponseEntity(HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }

    public UserDTO getCurrentUserFromToken(String mail) {
        ResponseEntity responseEntity = rabbitUserService.findByMail(mail);
        UserDTO userDTO = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            userDTO = (UserDTO) responseEntity.getBody();
            userDTO.setPassword("");
            log.info("Get User from Token -> {}" , userDTO);
            return userDTO;
        }
        return null;
    }
    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie(jwtSettingsProvider.getCookieAuthTokenName(), token);
        cookie.setPath(jwtSettingsProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtSettingsProvider.getCookieAuthExpiration());
        return cookie;
    }
}
