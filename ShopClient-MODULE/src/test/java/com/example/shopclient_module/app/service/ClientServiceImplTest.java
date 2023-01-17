package com.example.shopclient_module.app.service;

import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.domain.Status;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.exception.RegistrationException;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    private ClientShopRepository repository;
    @Autowired
    private ClientServiceImpl clientService;

    private UserDto userDto = new UserDto("testName",
            "testLastName",
            "testMail",
            "pass",
            "pass",
            Status.ACTIVE,
            Role.USER,
            "testAddress",
            1l );

    @Test
    @Transactional
    void registrationClient() {
        clientService.registrationClient(userDto);

        ShopClient client = repository.findByMail(userDto.getMail()).get();

        assertNotNull(client);
        assertEquals(client.getName(), userDto.getFirstName());
        assertEquals(client.getLastname(), userDto.getLastName());
        assertEquals(client.getStatus(), userDto.getStatus());
        assertEquals(client.getAddress(), userDto.getAddress());
        assertEquals(client.getRole(), userDto.getRole());
    }
    @Test
    @Transactional
    void registrationPasswordEmpty() {
        userDto.setMatchingPassword(" ");
        userDto.setPassword(" ");
        assertThrows(RegistrationException.class, () -> {
            UserDto saveUser = clientService.registrationClient(userDto);
                });
    }
    @Test
    @Transactional
    void registrationPasswordDoesntMatch() {
        userDto.setMatchingPassword("anotherPass");
        assertThrows(RegistrationException.class, () -> {
            UserDto saveUser = clientService.registrationClient(userDto);
        });

    }

    @Test
    @Transactional
    void updateClientAlreadyExist() {
        UserDto userFirst = clientService.registrationClient(userDto);
        assertThrows(RegistrationException.class, () -> {
            UserDto userSecond = clientService.registrationClient(userDto);
        });

    }

    @Test
    @Transactional
    void addBucket() {
        repository.save(new ShopClient(userDto));
        var mail = userDto.getMail();
        var updateBucket = 2l;
        clientService.addBucket(mail,updateBucket);
        ShopClient clientFromDB = repository.findByMail(userDto.getMail()).get();

        assertEquals(clientFromDB.getBucketId(), updateBucket);
    }
    @Test
    @Transactional
    void addBucketUserByMailNotFound() {
        repository.save(new ShopClient(userDto));
        var mail = "errorMail";
        var updateBucket = 2l;

        assertThrows(RuntimeException.class, () -> {
            clientService.addBucket(mail,updateBucket);
        });

        ShopClient clientFromDB = repository.findByMail(userDto.getMail()).get();
        assertNotEquals(clientFromDB.getBucketId(), updateBucket);
        assertEquals(clientFromDB.getBucketId(), userDto.getBucketId());
    }

    @Test
    @Transactional
    void findAllClient() {
        repository.deleteAll();
        repository.save(new ShopClient(userDto));
        List<UserDto> listFromDb = clientService.findAllClient();

        assertNotNull(listFromDb);
        assertEquals(listFromDb.size(), 1);
        assertEquals(listFromDb.stream().findFirst().get().getMail(), userDto.getMail());
    }

    @Test
    @Transactional
    void findAllClientEmptyDB() {
        repository.deleteAll();
       assertThrows(RegistrationException.class, () -> {
           List<UserDto> listFromDb = clientService.findAllClient();
       });

    }

    @Test
    @Transactional
    void findByMail() {
        repository.save(new ShopClient(userDto));

        UserDto userFromDb = clientService.findByMail(userDto.getMail());

        assertNotNull(userFromDb);
        assertNotEquals(userFromDb, userDto);
        assertNotEquals(userFromDb.getPassword(), userDto.getPassword());
        assertNotEquals(userFromDb.getMatchingPassword(), userDto.getMatchingPassword());
        assertEquals(userFromDb.getFirstName(), userDto.getFirstName());
        assertEquals(userFromDb.getLastName(), userDto.getLastName());
        assertEquals(userFromDb.getStatus(), userDto.getStatus());
        assertEquals(userFromDb.getAddress(), userDto.getAddress());
        assertEquals(userFromDb.getRole(), userDto.getRole());

        assertTrue(userFromDb.getPassword() == null);
        assertTrue(userFromDb.getMatchingPassword() == null );
    }
    @Test
    @Transactional
    void findByMailNotFound() {
        UserDto userFromDb = clientService.findByMail(userDto.getMail());

        assertTrue(userFromDb == null);
    }
}