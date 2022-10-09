package com.example.shop_module.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;


@Component
@Getter
public class JwtSettingsProvider {
    @Value("${auth.cookie.auth}")
    private String cookieAuthTokenName;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${auth.cookie.expiration-auth}")
    private Integer cookieAuthExpiration;

    @Value("${auth.cookie.path}")
    private  String cookiePath;


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
