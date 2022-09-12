package com.example.eurikaclient.provider;


import com.example.eurikaclient.model.UserRequest;
import com.example.eurikaclient.model.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtSettingsProvider jwtSettingsProvider;
    private final RestTemplate restTemplate;

    public String getMailFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSettingsProvider.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token)  {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSettingsProvider.getSecretKey()).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Jwt token is expired or invalid");
        }
    }

    public String resolveToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null ){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(jwtSettingsProvider.getCookieAuthTokenName())) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

    public void login  (HttpServletResponse response, String email, String password) throws JSONException {
        String url = "http://localhost:8082/api/v1/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
            personJsonObject.put("mail", email);
            personJsonObject.put("password", password);
        HttpEntity request = new HttpEntity(personJsonObject.toString(), headers);

        ResponseEntity<String> respToken = restTemplate.postForEntity(url, request, String.class);

        JSONObject requestToken = new JSONObject(respToken.getBody());
            String token = (String) requestToken.get("token");

            Cookie cookie = new Cookie(jwtSettingsProvider.getCookieAuthTokenName(), token);
            cookie.setPath(jwtSettingsProvider.getCookiePath());
            cookie.setHttpOnly(true);
            cookie.setMaxAge(jwtSettingsProvider.getCookieAuthExpiration());

        response.addCookie(cookie);
    }

    public void registration  (HttpServletResponse response, UserRequest userRequest) throws JSONException {
        String url = "http://localhost:8082/api/v1/auth/registration";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();

        personJsonObject.put("mail", userRequest.getMail());
        personJsonObject.put("password", userRequest.getPassword());
        personJsonObject.put("firstName", userRequest.getFirstName());
        personJsonObject.put("lastName", userRequest.getLastName());
        personJsonObject.put("status", userRequest.getStatus());

        HttpEntity requestBody = new HttpEntity(personJsonObject.toString(), headers);
        ResponseEntity<String> respToken = restTemplate.postForEntity(url, requestBody, String.class);
        JSONObject requestToken = new JSONObject(respToken.getBody());
        String token = (String) requestToken.get("token");
        Cookie cookie = new Cookie(jwtSettingsProvider.getCookieAuthTokenName(), token);
        cookie.setPath(jwtSettingsProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtSettingsProvider.getCookieAuthExpiration());
        response.addCookie(cookie);
    }

    public UserResponse getCurrentUserFromToken(String token) throws JSONException {
        String url = "http://localhost:8082/api/v1/auth/current";
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject tokenJsonObject = new JSONObject();
            tokenJsonObject.put("token", token);
            HttpEntity req = new HttpEntity(tokenJsonObject.toString(), headers);
            ResponseEntity<UserResponse> responseEntity = restTemplate.postForEntity(url, req, UserResponse.class);
            UserResponse userResponse = responseEntity.getBody();
            return userResponse;
        }
        return new UserResponse();
    }

}
