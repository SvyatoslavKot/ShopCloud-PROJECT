package com.example.authservice.app.mq.rabbitMq;

import com.example.authservice.app.model.Role;
import com.example.authservice.app.model.Status;
import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import com.example.authservice.app.requestBeans.UserRequest;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ShopAuthListenerTest {

    @Autowired
    UserRepository repository;

    @Autowired
    ShopAuthListener listener;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    private String secretKey = Base64.getEncoder().encodeToString("secretKey".getBytes());

    private User testUser = new User(1l, "testMail", "FirstName", "LastName", "pass", Role.USER, Status.ACTIVE);
    private HashMap<String, String> requestMap = new HashMap<>();

    @Test
    @Transactional
    void consume2() {
        testUser.setPassword(passwordEncoder.encode(testUser.getPassword()));
        repository.save(testUser);
        requestMap.put("mail", testUser.getMail());
        requestMap.put("password", "pass");
        Map<Object, Object> responseMap = listener.consume2( requestMap);

        String token = (String) responseMap.get(HttpStatus.OK);
        String nameFromToken = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        String roleFromToken = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role");

        assertTrue(token != null);
        assertTrue(responseMap.get(HttpStatus.OK)!= null);
        assertEquals(nameFromToken, testUser.getMail());
        assertEquals(roleFromToken, testUser.getRole().name());


    }

    @Test
    @Transactional
    void consumeUserNotFound() {
        requestMap.put("mail", testUser.getMail());
        requestMap.put("password", "pass");
        Map<Object, Object> responseMap = listener.consume2( requestMap);

        String responseMsg = (String) responseMap.get(HttpStatus.NON_AUTHORITATIVE_INFORMATION);

        assertEquals("Пользователь " + testUser.getMail() + " не зарегистрирован.", responseMsg);
    }
}