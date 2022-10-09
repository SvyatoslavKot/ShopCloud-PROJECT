package com.example.eurikaclient.provider;


import com.example.eurikaclient.jwtBeans.UserRequest;
import com.example.eurikaclient.jwtBeans.UserResponse;
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
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
@AllArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtSettingsProvider jwtSettingsProvider;

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


}
