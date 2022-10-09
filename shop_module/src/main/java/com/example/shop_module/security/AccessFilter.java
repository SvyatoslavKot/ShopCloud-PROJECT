package com.example.shop_module.security;

import com.example.shop_module.domain.Role;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.mq.ProducerShopClient;
import com.example.shop_module.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@Component
@Slf4j
@AllArgsConstructor
public class AccessFilter implements Filter{

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IllegalArgumentException, IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (token != null && !token.isEmpty()) {
            try {
                jwtTokenProvider.validateToken(token);
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    if (authentication != null){
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            } catch (JwtAthenticationException e) {
                SecurityContextHolder.clearContext();
                e.printStackTrace();
                log.error(e.getMessage());
            }catch (IllegalArgumentException ie) {
                SecurityContextHolder.clearContext();
                ie.printStackTrace();
                log.error(ie.getMessage());
            }
        }
        filterChain.doFilter(httpServletRequest, response);
    }
}
