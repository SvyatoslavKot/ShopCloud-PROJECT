package com.example.shop_module.app.security;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.mq.ProduserAuthModule;
import com.example.shop_module.app.service.AuthService;
import com.example.shop_module.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class JwtTokenProviderService {

    private final JwtSettingsProvider jwtSettingsProvider;
    private final ProduserAuthModule produserAuthModule;
    private final AuthService rabbitAuthService;
    private final UserService rabbitUserService;

    public JwtTokenProviderService(JwtSettingsProvider jwtSettingsProvider,
                                   @Qualifier("rabbitProduceAuthModule") ProduserAuthModule produserAuthModule,
                                   @Qualifier("rabbitAuthService") AuthService rabbitAuthService,
                                   @Qualifier("rabbitUserService") UserService rabbitUserService) {
        this.jwtSettingsProvider = jwtSettingsProvider;
        this.produserAuthModule = produserAuthModule;
        this.rabbitAuthService = rabbitAuthService;
        this.rabbitUserService = rabbitUserService;
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
        /*
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("service login " +" mail " + email + " passwor " + password);
        System.out.println(userRepository.findByMail(email));

        requestJsonObject.put("mail", email);
        requestJsonObject.put("password", password);
        HttpEntity request = new HttpEntity(requestJsonObject.toString(), headers);

        ResponseEntity<String> respToken = restTemplate.postForEntity(AUTH_LOGIN_URL, request, String.class);

        JSONObject requestToken = new JSONObject(respToken.getBody());
        String token = (String) requestToken.get("token");
        */
      //  String authorizationResponse = produserAuthModule.authorization(email,password);

       // Cookie cookie = createCookie(token);

        //response.addCookie(cookie);
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
