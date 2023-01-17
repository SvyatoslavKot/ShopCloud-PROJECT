package com.example.authservice.app.security;

import com.example.authservice.app.model.Role;
import com.example.authservice.app.model.Status;
import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProvaderTest {

    @Autowired
    private JwtTokenProvader jwtTokenProvader;
    @Autowired
    private UserRepository repository;

    private String secretKey = Base64.getEncoder().encodeToString("secretKey".getBytes());
    private String userName = "testMail";
    private String role = Role.USER.name();
    private Date now = new Date();
    String controlToken;

    private User testUser = new User(1l, "testMail", "FirstName", "LastName", "pass", Role.USER, Status.ACTIVE);

    @BeforeEach
    void before() {
        Date validity = new Date(now.getTime() + 60488 * 1000);
        Claims claims = Jwts.claims().setSubject(testUser.getMail());
        claims.put("role", role);
        controlToken =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    @Test
    void createToken() {
        String testToken = jwtTokenProvader.createToken(userName, role);

        String nameTestToken = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(testToken).getBody().getSubject();
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(testToken).getBody().getExpiration();
        String roleTestToken = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(testToken).getBody().get("role");

        assertEquals(nameTestToken, userName);
        assertTrue(expiration.getTime() > now.getTime());
        assertEquals(roleTestToken, role);

    }

    @Test
    void validateToken() {
        assertTrue(jwtTokenProvader.validateToken(controlToken));

    }
    @Test
    void validateTokenExpired() {
        Date validity = new Date(now.getTime() - 1);
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("role", role);
        String expiredToken =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        assertThrows(JwtAthenticationException.class, () -> {
            assertTrue(jwtTokenProvader.validateToken(expiredToken));
        });
    }

    @Test
    void validateTokenInvalid() {
        Date validity = new Date(now.getTime() - 1);
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("role", role);
        String invalidSecretToken =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,"anotherSercret")
                .compact();

        assertThrows(JwtAthenticationException.class, () -> {
            assertTrue(jwtTokenProvader.validateToken(invalidSecretToken));
        });

        assertThrows(JwtAthenticationException.class, () -> {
            assertTrue(jwtTokenProvader.validateToken("INVALID-TOKEN"));
        });
    }

    @Test
    @Transactional
    void getAuthentication() {
        repository.save(testUser);
        Authentication auth =  jwtTokenProvader.getAuthentication(controlToken);

        assertNotNull(auth);
        assertEquals(auth.getName(), testUser.getMail());
        assertEquals(auth.getCredentials(), "");
        assertEquals(auth.getAuthorities().stream().findFirst().get(), testUser.getRole().getAuthority().stream().findFirst().get());
    }

    @Test
    void getUsername() {
        String mail = jwtTokenProvader.getUsername(controlToken);
        assertEquals(mail, userName);
    }

    @Test
    void resolveToken() {
    }
}