package com.example.shop_module.app.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@Component
@Slf4j
@AllArgsConstructor
public class AccessFilter implements Filter{

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IllegalArgumentException, IOException, ServletException, AuthenticationException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (token != null && !token.isEmpty()) {
            try {
                    jwtTokenProvider.validateToken(token);
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    if (authentication != null){
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else {
                        jwtTokenProvider.cleanAuthCookie(httpServletResponse,httpServletRequest);
                        SecurityContextHolder.clearContext();
                    }
            } catch (AuthenticationException | JwtAthenticationException | IllegalArgumentException e) {
                log.info("catch");
                jwtTokenProvider.cleanAuthCookie(httpServletResponse,httpServletRequest);
                SecurityContextHolder.clearContext();
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
