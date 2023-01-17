package com.example.shop_module.app.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionObjectHolder {
    private long amountClick = 0;

    public SessionObjectHolder(){
        System.out.println("Session object created.");
    }

    public long getAmountClick(){
        return amountClick;
    }
    public void addClick(){
        amountClick++;
    }

}
