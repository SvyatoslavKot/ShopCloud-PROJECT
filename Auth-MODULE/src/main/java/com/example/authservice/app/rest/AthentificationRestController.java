package com.example.authservice.app.rest;


import com.example.authservice.app.model.Role;
import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import com.example.authservice.app.requestBeans.TokenResponse;
import com.example.authservice.app.requestBeans.UserRequest;
import com.example.authservice.app.requestBeans.UserResponse;
import com.example.authservice.app.security.JwtTokenProvader;
import com.example.authservice.app.service.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AthentificationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvader jwtTokenProvader;
    private final AppUserService appUserService;

    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> athenticate(@RequestBody AuthenticationRequestDTO request, HttpServletResponse response){
        try{
            String email = request.getMail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword()));
            User user = userRepository.findByMail(request.getMail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

            String token = jwtTokenProvader.createToken(request.getMail(), user.getRole().name());
            System.out.println("prov:" + jwtTokenProvader.getUsername(token));
            Map<Object,Object> map = new HashMap<>();
            map.put("token", token);
            return ResponseEntity.ok(map);
            /*
            Cookie cookie = new Cookie("AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(600000);
            response.addCookie(cookie);

            return buildUserResponse(user);

             */

        }catch (AuthenticationException e){
            clearAuthToken(response);
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    @PostMapping(value = "/registration", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> registration(@RequestBody UserRequest request, HttpServletResponse response){
        try{
            if (userRepository.findByMail(request.getMail()).isPresent()){
                return new ResponseEntity<>("Email is taken ", HttpStatus.FORBIDDEN);
            }

            User user = new User(request);
            user.setRole(Role.USER);
            appUserService.saveUser(user);
            String token = jwtTokenProvader.createToken(user.getMail(), user.getRole().name());
            Map<Object,Object> map = new HashMap<>();
            map.put("token", token);
            return ResponseEntity.ok(map);
            /*
            Cookie cookie = new Cookie("AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(600000);
            response.addCookie(cookie);

            return buildUserResponse(user);

             */

        }catch (AuthenticationException e){
            clearAuthToken(response);
            return new ResponseEntity<>("Something went wrong " + e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/current",  produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> current(@RequestBody TokenResponse token){
            System.out.println("current token " + token.getToken() );
            User user = userRepository.findByMail(jwtTokenProvader.getUsername(token.getToken())).orElse(new User());
            return buildUserResponse(user);

        /*
        try{
            User appUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return buildUserResponse(appUser);
        }catch (NullPointerException e){
            log.error(e.getMessage());
        }
        return buildUserResponse(new User());

         */
    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
    }

    private ResponseEntity<?> buildUserResponse(User user){
        return ResponseEntity.ok(new UserResponse(user));
    }

    private void clearAuthToken( HttpServletResponse response){
        Cookie authCookie = new Cookie("AUTH-TOKEN", "-");
        authCookie.setPath("/");
        response.addCookie(authCookie);
    }

}
