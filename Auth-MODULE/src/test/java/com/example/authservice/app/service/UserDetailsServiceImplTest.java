package com.example.authservice.app.service;

import com.example.authservice.app.model.Role;
import com.example.authservice.app.model.Status;
import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;
    private User testUser = new User(1l, "testMail", "FirstName", "LastName", "pass", Role.USER, Status.ACTIVE);

    @Test
    @Transactional
    void loadUserByUsername() {
        userRepository.save(testUser);
        UserDetails userDetails = userDetailsService.loadUserByUsername(testUser.getMail());

        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername() , testUser.getMail());
        assertEquals(userDetails.getPassword(), testUser.getPassword());
        assertEquals(userDetails.getAuthorities().stream().findFirst().get(), testUser.getRole().getAuthority().stream().findFirst().get());
    }
}