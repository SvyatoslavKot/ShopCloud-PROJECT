package com.example.shopclient_module.service;

import com.example.shopclient_module.domain.Role;
import com.example.shopclient_module.domain.ShopClient;
import com.example.shopclient_module.domain.Status;
import com.example.shopclient_module.dto.UserDto;
import com.example.shopclient_module.rabbitMq.producers.AuthProducer;
import com.example.shopclient_module.repository.ClientShopRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService{
    private final ClientShopRepository clientRepository;
    private final AuthProducer authProducer;

    @Override
    public UserDto registrationClient(UserDto requestClient) {
        if (!clientRepository.findByMail(requestClient.getMail()).isPresent()){
            requestClient.setRole(Role.USER);
            requestClient.setStatus(Status.ACTIVE);
            clientRepository.save(new ShopClient(requestClient));
            authProducer.newClientEvent(requestClient);
            return requestClient;
        }
        return null;
    }

    @Override
    public void updateClient(UserDto userDto) {
        ShopClient client = clientRepository.findByMail(userDto.getMail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (client != null){
            client.setAddress(userDto.getAddress());
            client.setName(userDto.getFirstName());
            client.setLastname(userDto.getLastName());
            System.out.println("dto -> " + client);
            clientRepository.save(client);
        }
    }

    @Override
    public void addBucket(String mail, Long bucketId) {
        ShopClient client = clientRepository.findByMail(mail).orElseThrow(()-> new RuntimeException("User not found"));
        client.setBucketId(bucketId);
        clientRepository.save(client);
    }
}
