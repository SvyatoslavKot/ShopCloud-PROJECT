package com.example.authservice.app.mq.rabbitMq;

import com.example.authservice.app.model.Role;
import com.example.authservice.app.model.Status;
import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import com.example.authservice.app.requestBeans.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopclientListenerTest {

    @Autowired
    UserRepository repository;

    @Autowired
    ShopclientListener listener;

    private UserRequest requestUser = new UserRequest();
    private User testUser = new User(1l, "testMail", "FirstName", "LastName", "pass", Role.USER, Status.ACTIVE);

    @Test
    @Transactional
    void consume2() {
        requestUser.setMail(testUser.getMail());
        requestUser.setFirstName("anotherName");
        requestUser.setLastName("anotherLastName");
        requestUser.setPassword("newPass");
        requestUser.setRole(Role.ADMIN);
        requestUser.setStatus(Status.BANNED);

        listener.consume2( requestUser);

        User userFromDB = repository.findByMail(testUser.getMail()).get();

        assertEquals(userFromDB.getFirstName(),  requestUser.getFirstName());
        assertEquals(userFromDB.getLastName(),  requestUser.getLastName());
        assertEquals(userFromDB.getPassword(),  requestUser.getPassword());
        assertEquals(userFromDB.getRole(),  requestUser.getRole());
        assertEquals(userFromDB.getStatus(),  requestUser.getStatus());
    }

    @Test
    @Transactional
    void updateClient() {
        repository.save(testUser);

        requestUser.setMail(testUser.getMail());
        requestUser.setFirstName("anotherName");
        requestUser.setLastName("anotherLastName");
        requestUser.setPassword("newPass");
        requestUser.setRole(Role.ADMIN);
        requestUser.setStatus(Status.BANNED);

        listener.updateClient(requestUser);

        User userFromDB = repository.findByMail(testUser.getMail()).get();

        assertEquals(userFromDB.getFirstName(),  requestUser.getFirstName());
        assertEquals(userFromDB.getLastName(),  requestUser.getLastName());
        assertNotEquals(userFromDB.getPassword(),  requestUser.getPassword());
        assertNotEquals(userFromDB.getRole(),  requestUser.getRole());
        assertNotEquals(userFromDB.getStatus(),  requestUser.getStatus());

        assertEquals(userFromDB.getPassword(),  testUser.getPassword());
        assertEquals(userFromDB.getRole(),  testUser.getRole());
        assertEquals(userFromDB.getStatus(),  testUser.getStatus());
    }
}