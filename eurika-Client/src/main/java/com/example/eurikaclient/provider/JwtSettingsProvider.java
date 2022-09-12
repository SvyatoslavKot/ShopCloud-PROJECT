package com.example.eurikaclient.provider;

import io.jsonwebtoken.*;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;

@Component
@Getter
public class JwtSettingsProvider {

    @Value("${auth.cookie.auth}")
    private String cookieAuthTokenName;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${auth.cookie.expiration-auth}")
    private Integer cookieAuthExpiration;

    @Value("${auth.cookie.path}")
    private  String cookiePath;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
