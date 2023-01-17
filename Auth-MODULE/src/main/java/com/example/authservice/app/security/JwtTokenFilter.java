package com.example.authservice.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvader jwtTokenProvader;

    @Autowired
    public JwtTokenFilter(JwtTokenProvader jwtTokenProvader) {
        this.jwtTokenProvader = jwtTokenProvader;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvader.resolveToken((HttpServletRequest) request);
        try{
            if(token!= null && jwtTokenProvader.validateToken(token)){
                Authentication authentication = jwtTokenProvader.getAuthentication(token);
                if (authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (JwtAthenticationException e){
            SecurityContextHolder.clearContext();

            ((HttpServletResponse)response).sendError(e.getHttpStatus().value());
            throw new JwtAthenticationException("JWT token is expired or invalid");
        }
        chain.doFilter(request,response);
    }
}
