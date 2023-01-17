package com.example.shopclient_module.app.controller;

import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.domain.Status;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReactControllerTest {

    @Autowired
    ClientShopRepository repository;

    @Autowired
    ReactController reactController;

    @Test
    @Transactional
    void getByMail() {
        ShopClient client = new ShopClient();
        client.setMail("testMail");
        client.setName("testName");
        client.setLastname("testLastname");
        client.setAddress("testAddress");
        client.setRole(Role.USER);
        client.setBucketId(1l);
        client.setStatus(Status.ACTIVE);
        repository.save(client);

        Mono<ResponseEntity<UserDto>> mono = reactController.getByMail(client.getMail());

        ResponseEntity<UserDto> responseEntity = mono.block();

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getMail(), client.getMail());
        assertEquals(responseEntity.getBody().getFirstName(), client.getName());
        assertEquals(responseEntity.getBody().getLastName(), client.getLastname());
        assertEquals(responseEntity.getBody().getAddress(), client.getAddress());
        assertEquals(responseEntity.getBody().getRole(), client.getRole());
        assertEquals(responseEntity.getBody().getBucketId(), client.getBucketId());
        assertEquals(responseEntity.getBody().getStatus(), client.getStatus());
    }

    @Test
    @Transactional
    void getByMailNullUser() {
        var testMail = "unknownMail";

        Mono<ResponseEntity<UserDto>> responseMono = reactController.getByMail(testMail);
        ResponseEntity<UserDto> responseEntity = responseMono.block();

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.RESET_CONTENT);
        var headerMsg = responseEntity.getHeaders().get("message").stream().findFirst().get();
        assertEquals(headerMsg, "User with mail: " + testMail + " not found!");

    }
}