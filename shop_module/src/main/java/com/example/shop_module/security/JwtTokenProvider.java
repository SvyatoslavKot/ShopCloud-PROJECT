package com.example.shop_module.security;

import com.example.shop_module.domain.Role;
import com.example.shop_module.domain.User;
import com.example.shop_module.mq.ProducerShopClient;
import com.example.shop_module.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@AllArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final JwtTokenProviderService jwtTokenProviderService;
    private final JwtSettingsProvider jwtSettingsProvider;

    public boolean validateToken(String token) throws JwtAthenticationException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSettingsProvider.getSecretKey()).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtAthenticationException("Jwt token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token){
        User user = jwtTokenProviderService.getCurrentUserFromToken(getUsername(token));
        UserDetails userDetail = new org.springframework.security.core.userdetails.User(
                        user.getMail(),
                        user.getPassword(),
                        user.getRole().getAuthority());
        return new UsernamePasswordAuthenticationToken( userDetail,"" ,userDetail.getAuthorities());
    }

    public String getUsername(String token){
        try{
            String parseToken = Jwts.parser().setSigningKey(jwtSettingsProvider.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
            return parseToken;
        }catch (JwtException e ){
            log.error(e.getMessage());
            return null;
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
