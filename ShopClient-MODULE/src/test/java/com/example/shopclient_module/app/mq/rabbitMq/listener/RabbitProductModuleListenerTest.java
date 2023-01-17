package com.example.shopclient_module.app.mq.rabbitMq.listener;

import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.domain.Status;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RabbitProductModuleListenerTest {

    @Autowired
    private ClientShopRepository repository;
    @Autowired
    private RabbitProductModuleListener listener;

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
    void updateClientBucket() {
        repository.save(new ShopClient(userDto));
        ShopClient clientAfterUpdate;
        UserDto updateUserDto = null;
        try {
            updateUserDto = userDto.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        updateUserDto.setBucketId(2l);
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("mail", updateUserDto.getMail());
        requestMap.put("bucketId", updateUserDto.getBucketId().intValue());
        listener.updateClientBucket(requestMap);
        clientAfterUpdate = repository.findByMail(userDto.getMail()).get();


        assertEquals(userDto.getFirstName(), clientAfterUpdate.getName());
        assertEquals(userDto.getLastName(), clientAfterUpdate.getLastname());
        assertEquals(userDto.getAddress(), clientAfterUpdate.getAddress());
        assertEquals(userDto.getRole(), clientAfterUpdate.getRole());
        assertEquals(userDto.getStatus(), clientAfterUpdate.getStatus());
        assertNotEquals(userDto.getBucketId(), clientAfterUpdate.getBucketId());
        assertEquals(clientAfterUpdate.getBucketId(), 2l);

    }

    @Test
    @Transactional
    void updateClientBucketNotFoundByMail() throws CloneNotSupportedException {
        repository.save(new ShopClient(userDto));
        UserDto updateUserDto = null;
        ShopClient clientAfterUpdate;
        try {
            updateUserDto = userDto.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        updateUserDto.setBucketId(2l);
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("mail", "notExistMail");
        requestMap.put("bucketId", updateUserDto.getBucketId().intValue());
        listener.updateClientBucket(requestMap);
        clientAfterUpdate = repository.findByMail(userDto.getMail()).get();

        assertNotEquals(clientAfterUpdate.getBucketId(),updateUserDto.getBucketId());
        assertEquals(clientAfterUpdate.getBucketId(), userDto.getBucketId());
    }
}