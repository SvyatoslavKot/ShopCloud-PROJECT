package com.example.shop_module.app.service.abstraction;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    ResponseEntity authorization(String email, String password);
}
