package com.example.shopclient_module.app.mq.rabbitMq.listener;

import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.domain.Status;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.exception.RegistrationException;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RabbitShopModuleListenerTest {

    @Autowired
    private ClientShopRepository repository;
    @Autowired
    private RabbitShopModuleListener rabbitListener;

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
    void newClient() {
         rabbitListener.newClient(userDto);
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
    void newClientDoubleMail() {
        UserDto userFirst = rabbitListener.newClient(userDto);
        UserDto userSecond = rabbitListener.newClient(userDto);

        assertEquals(userFirst, userDto);

        assertNotNull(userFirst);
        assertNotNull(userSecond);

        assertNotEquals(userFirst.getMail(), userSecond.getMail());
        assertEquals(userFirst.getMail(), userDto.getMail());
        assertEquals(userSecond.getMail(), null);
    }
    @Test
    @Transactional
    void newClientPasswordDoesntMatch() {
        userDto.setMatchingPassword("anotherPass");
        UserDto saveUser = rabbitListener.newClient(userDto);

        assertNotEquals(saveUser, userDto);
        assertNotNull(saveUser);
        assertEquals(saveUser, new UserDto());
    }
    @Test
    @Transactional
    void newClientPasswordEmpty() {
        userDto.setMatchingPassword(" ");
        userDto.setPassword(" ");
        UserDto saveUser = rabbitListener.newClient(userDto);

        assertNotEquals(saveUser, userDto);
        assertNotNull(saveUser);
        assertEquals(saveUser, new UserDto());
    }
    @Test
    @Transactional
    void newClientAnotherRoleAndStatus() {
        userDto.setRole(Role.ADMIN);
        userDto.setStatus(Status.BANNED);
        UserDto saveUser = rabbitListener.newClient(userDto);

        assertEquals(saveUser, userDto);

        assertEquals(saveUser.getRole(), Role.ADMIN);
        assertEquals(saveUser.getStatus(), Status.BANNED);
    }
    @Test
    @Transactional
    void newClientRoleAndStatusNull() {
        userDto.setRole(null);
        userDto.setStatus(null);
        UserDto saveUser = rabbitListener.newClient(userDto);

        assertEquals(saveUser, userDto);

        assertEquals(saveUser.getRole(), Role.USER);
        assertEquals(saveUser.getStatus(), Status.ACTIVE);
    }

    @Test
    @Transactional
    void getClient(){
        repository.save(new ShopClient(userDto));

        UserDto userFromDb = rabbitListener.getClient(userDto.getMail());

        assertNotNull(userFromDb);
        assertNotEquals(userFromDb, userDto);
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
    void getClientNull(){
        var nullClientMail = "nullClientMail";
        UserDto userFromDb = rabbitListener.getClient(nullClientMail);

        assertTrue(userFromDb == null );
    }

    @Test
    @Transactional
    void updateClient() {
        repository.save(new ShopClient(userDto));
        UserDto updateUserDto = null;
        try {
            updateUserDto = userDto.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        updateUserDto.setFirstName("updateName");
        updateUserDto.setLastName("updateLastName");
        updateUserDto.setAddress("updateAddress");
        updateUserDto.setRole(Role.ADMIN);
        updateUserDto.setStatus(Status.BANNED);
        updateUserDto.setBucketId(3l);
        rabbitListener.updateClient(updateUserDto);

        ShopClient clientFromDb = repository.findByMail(userDto.getMail()).get();

        assertEquals   (clientFromDb.getName(),     updateUserDto.getFirstName());
        assertEquals   (clientFromDb.getLastname(), updateUserDto.getLastName());
        assertEquals   (clientFromDb.getAddress(),  updateUserDto.getAddress());
        assertEquals   (clientFromDb.getBucketId(), updateUserDto.getBucketId());
        assertNotEquals(clientFromDb.getRole(),     updateUserDto.getRole());
        assertNotEquals(clientFromDb.getStatus(),   updateUserDto.getStatus());


        assertEquals(clientFromDb.getRole(), userDto.getRole());
        assertEquals(clientFromDb.getStatus(), userDto.getStatus());
    }

    @Test
    @Transactional
    void findAllClient() {
        repository.deleteAll();
        repository.save(new ShopClient(userDto));
        List<UserDto> userDtoList = rabbitListener.findAllClient();

        assertNotNull(userDtoList);
        assertEquals(userDtoList.size() , 1);
        assertEquals(userDtoList.stream().findFirst().get().getMail(), userDto.getMail());
    }

    @Test
    @Transactional
    void findAllClientEmptyDB() {
        repository.deleteAll();
        List<UserDto> userDtoList = rabbitListener.findAllClient();

        assertTrue(userDtoList == null);
    }

}