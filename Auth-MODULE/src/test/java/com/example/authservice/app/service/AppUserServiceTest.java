package com.example.authservice.app.service;

import com.example.authservice.app.model.Role;
import com.example.authservice.app.model.Status;
import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import com.example.authservice.app.requestBeans.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserServiceTest {

    @Autowired
    UserRepository repository;
    @Autowired
    AppUserService userService;

    private User testUser = new User(1l, "testMail", "FirstName", "LastName", "pass", Role.USER, Status.ACTIVE);

    @Test
    @Transactional
    void saveUser() {
        userService.saveUser(testUser);
        User userFromDB = repository.findByMail(testUser.getMail()).get();

        assertNotNull(userFromDB);
        assertEquals(userFromDB.getFirstName(), testUser.getFirstName());
        assertEquals(userFromDB.getLastName(), testUser.getLastName());
        assertEquals(userFromDB.getPassword(), testUser.getPassword());
        assertEquals(userFromDB.getRole(), testUser.getRole());
        assertEquals(userFromDB.getStatus(), testUser.getStatus());
    }
    @Test
    @Transactional
    void saveUserAlredyExist() throws CloneNotSupportedException {
        userService.saveUser(testUser);
        User secondUser = testUser.clone();
        secondUser.setFirstName("anotherName");
        secondUser.setLastName("anotherLastName");
        userService.saveUser(secondUser);
        User userFromDB = repository.findByMail(testUser.getMail()).get();

        assertNotNull(userFromDB);
        assertEquals(userFromDB.getFirstName(), testUser.getFirstName());
        assertEquals(userFromDB.getLastName(), testUser.getLastName());
        assertNotEquals(userFromDB.getFirstName(), secondUser.getFirstName());
        assertNotEquals(userFromDB.getLastName(), secondUser.getLastName());
    }

    @Test
    @Transactional
    void findByMail() {
        repository.save(testUser);
        User userFromDB = userService.findByMail(testUser.getMail());

        assertNotNull(userFromDB);
        assertEquals(userFromDB.getFirstName(), testUser.getFirstName());
        assertEquals(userFromDB.getLastName(), testUser.getLastName());
        assertEquals(userFromDB.getPassword(), testUser.getPassword());
        assertEquals(userFromDB.getRole(), testUser.getRole());
        assertEquals(userFromDB.getStatus(), testUser.getStatus());

    }
    @Test
    void findByMailNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            User userFromDB = userService.findByMail(testUser.getMail());
        });

    }

    @Test
    @Transactional
    void updateUser()  {
        repository.save(testUser);

        UserRequest updateUser = new UserRequest();
        updateUser.setMail(testUser.getMail());
        updateUser.setFirstName("anotherName");
        updateUser.setLastName("anotherLastName");
        updateUser.setPassword("newPass");
        updateUser.setRole(Role.ADMIN);
        updateUser.setStatus(Status.BANNED);

        userService.updateUser(updateUser);

        User userFromDB = repository.findByMail(testUser.getMail()).get();

        assertEquals(userFromDB.getFirstName(), updateUser.getFirstName());
        assertEquals(userFromDB.getLastName(), updateUser.getLastName());
        assertNotEquals(userFromDB.getPassword(), updateUser.getPassword());
        assertNotEquals(userFromDB.getRole(), updateUser.getRole());
        assertNotEquals(userFromDB.getStatus(), updateUser.getStatus());

        assertEquals(userFromDB.getPassword(), testUser.getPassword());
        assertEquals(userFromDB.getRole(), testUser.getRole());
        assertEquals(userFromDB.getStatus(), testUser.getStatus());
    }
}