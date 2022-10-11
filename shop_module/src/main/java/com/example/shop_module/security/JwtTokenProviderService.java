package com.example.shop_module.security;

import com.example.shop_module.domain.User;
import com.example.shop_module.exceptions.LoginClientException;
import com.example.shop_module.mq.ProducerShopClient;
import com.example.shop_module.mq.ProduserAuthModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class JwtTokenProviderService {

    private final JwtSettingsProvider jwtSettingsProvider;
    private final ProducerShopClient producerShopClient;
    private final ProduserAuthModule produserAuthModule;

    public ResponseEntity login  (HttpServletResponse response, String email, String password){
        Map<Object,Object> authorizationResponse = produserAuthModule.authorization(email,password);
        if (authorizationResponse != null){
            if(authorizationResponse.get(HttpStatus.OK.name()) != null){
                String token = (String) authorizationResponse.get(HttpStatus.OK.name());
                Cookie cookie = createCookie(token);
                response.addCookie(cookie);
                return new ResponseEntity(authorizationResponse.get(HttpStatus.OK.name()), HttpStatus.OK);
            }
            throw new LoginClientException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, (String)authorizationResponse
                    .get(HttpStatus.NON_AUTHORITATIVE_INFORMATION.name()));
        }
        throw  new LoginClientException(HttpStatus.NO_CONTENT, "Сервис не отвечает. Попробуйте позже.");
       /* headers.setContentType(MediaType.APPLICATION_JSON);
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

    public User getCurrentUserFromToken(String mail) {
        User user = new User(producerShopClient.getClientByMail(mail));
        user.setPassword("");
        return user;
    }
    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie(jwtSettingsProvider.getCookieAuthTokenName(), token);
        cookie.setPath(jwtSettingsProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtSettingsProvider.getCookieAuthExpiration());
        return cookie;
    }
}
