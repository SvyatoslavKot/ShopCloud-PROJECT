package com.example.authservice.rest;


import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtTokenProvader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AthentificationRestController {

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvader jwtTokenProvader;

    public AthentificationRestController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvader jwtTokenProvader) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvader = jwtTokenProvader;
    }

    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> athenticate(@RequestBody AuthenticationRequestDTO request){
        try{

            System.out.println("Rest Auth" + request.getMail());

            String email = request.getMail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword()));
            User user = userRepository.findByMail(request.getMail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
            String token = jwtTokenProvader.createToken(request.getMail(), user.getRole().name());
            Map<Object,Object> response = new HashMap<>();
            response.put("email", request.getMail());
            response.put("token", token);

            return ResponseEntity.ok(response);

        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logiut(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
    }

}
