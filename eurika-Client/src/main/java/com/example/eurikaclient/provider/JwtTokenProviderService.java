package com.example.eurikaclient.provider;

import com.example.eurikaclient.jwtBeans.UserRequest;
import com.example.eurikaclient.jwtBeans.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class JwtTokenProviderService {

    private final JwtSettingsProvider jwtSettingsProvider;
    private final RestTemplate restTemplate;

    private final String AUTH_LOGIN_URL = "http://localhost:8082/api/v1/auth/login";
    private final String AUTH_REGISTRATION_URL = "http://localhost:8082/api/v1/auth/registration";
    private final String AUTH_CURRENT_URL = "http://localhost:8082/api/v1/auth/current";

    private HttpHeaders headers = new HttpHeaders();
    private JSONObject requestJsonObject = new JSONObject();

    public JwtTokenProviderService(JwtSettingsProvider jwtSettingsProvider, RestTemplate restTemplate) {
        this.jwtSettingsProvider = jwtSettingsProvider;
        this.restTemplate = restTemplate;
    }

    public void login  (HttpServletResponse response, String email, String password) throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        requestJsonObject.put("mail", email);
        requestJsonObject.put("password", password);
        HttpEntity request = new HttpEntity(requestJsonObject.toString(), headers);

        ResponseEntity<String> respToken = restTemplate.postForEntity(AUTH_LOGIN_URL, request, String.class);

        JSONObject requestToken = new JSONObject(respToken.getBody());
        String token = (String) requestToken.get("token");

        Cookie cookie = new Cookie(jwtSettingsProvider.getCookieAuthTokenName(), token);
        cookie.setPath(jwtSettingsProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtSettingsProvider.getCookieAuthExpiration());

        response.addCookie(cookie);
    }

    public void registration  (HttpServletResponse response, UserRequest userRequest) throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        requestJsonObject.put("mail", userRequest.getMail());
        requestJsonObject.put("password", userRequest.getPassword());
        requestJsonObject.put("firstName", userRequest.getFirstName());
        requestJsonObject.put("lastName", userRequest.getLastName());
        requestJsonObject.put("status", userRequest.getStatus());

        HttpEntity requestBody = new HttpEntity(requestJsonObject.toString(), headers);
        ResponseEntity<String> respToken = restTemplate.postForEntity(AUTH_REGISTRATION_URL, requestBody, String.class);

        JSONObject requestToken = new JSONObject(respToken.getBody());
        String token = (String) requestToken.get("token");
        Cookie cookie = createCookie(token);
        response.addCookie(cookie);
    }

    public UserResponse getCurrentUserFromToken(String token) throws JSONException {
        if (token != null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestJsonObject.put("token", token);
            HttpEntity req = new HttpEntity(requestJsonObject.toString(), headers);
            ResponseEntity<UserResponse> responseEntity = restTemplate.postForEntity(AUTH_CURRENT_URL, req, UserResponse.class);
            UserResponse userResponse = responseEntity.getBody();
            return userResponse;
        }
        return new UserResponse();
    }

    public void logoutUser (HttpServletResponse response) {
        Cookie cookie = createCookie("");
        response.addCookie(cookie);
    }

    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie(jwtSettingsProvider.getCookieAuthTokenName(), token);
        cookie.setPath(jwtSettingsProvider.getCookiePath());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtSettingsProvider.getCookieAuthExpiration());
        return cookie;
    }
}
