package com.example.eurikaclient.provider;

import com.example.eurikaclient.jwtBeans.UserResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CurrentUserProvider {

    private final String ATTR_CURRENT_USER = "CurrentUser";

    public void set(HttpServletRequest request, UserResponse user) {
        request.setAttribute(ATTR_CURRENT_USER, user);
    }

    public UserResponse get(HttpServletRequest request) {
        UserResponse user = (UserResponse) request.getAttribute(ATTR_CURRENT_USER);
        if (user == null) {
            return new UserResponse();
        }
        return user;
    }

    public void clearRequestAttribute(HttpServletRequest request){
        request.setAttribute("CurrentUser", null);
    }
}
