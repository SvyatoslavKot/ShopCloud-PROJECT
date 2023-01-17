package com.example.shopclient_module.app.service;

import com.example.shopclient_module.app.exception.RegistrationException;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.domain.Status;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.mq.ProduceAuth;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService{

    private final ClientShopRepository clientRepository;
    private final ProduceAuth authProducer;

    public ClientServiceImpl(ClientShopRepository clientRepository, @Qualifier("rabbitProduceAuth") ProduceAuth authProducer) {
        this.clientRepository = clientRepository;
        this.authProducer = authProducer;
    }

    @Override
    public UserDto registrationClient(UserDto requestClient) {
        if (!clientRepository.findByMail(requestClient.getMail()).isPresent()){
            if (!requestClient.getPassword().isEmpty() && !requestClient.getPassword().equals(" ") &&
                requestClient.getPassword() != null && requestClient.getPassword().equals(requestClient.getMatchingPassword()))
            {
                requestClient.setRole(requestClient.getRole() != null ? requestClient.getRole() : Role.USER);
                requestClient.setStatus(requestClient.getStatus() != null ? requestClient.getStatus() : Status.ACTIVE);
                clientRepository.save(new ShopClient(requestClient));
                authProducer.newClientEvent(requestClient);
                return requestClient;
            }
            throw new RegistrationException(HttpStatus.CONFLICT, "Password don't match");
        }
        throw new RegistrationException(HttpStatus.CONFLICT, "Client with mail: " + requestClient.getMail() + " already exist!");
    }

    @Override
    public void updateClient(UserDto userDto) {
        ShopClient client = clientRepository.findByMail(userDto.getMail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (client != null){
            client.setBucketId(userDto.getBucketId() != null && userDto.getBucketId()!= 0 ? userDto.getBucketId() : client.getBucketId());
            client.setAddress(userDto.getAddress() != null ? userDto.getAddress() : client.getAddress() );
            client.setName(userDto.getFirstName() != null ? userDto.getFirstName() : client.getAddress() );
            client.setLastname(userDto.getLastName() != null ? userDto.getLastName() : client.getLastname());
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

    @Override
    public List<UserDto> findAllClient() {
        List<ShopClient> shopClients = clientRepository.findAll();
        List<UserDto> dtoList = new ArrayList<>();


        if (!shopClients.isEmpty()){
            shopClients.stream().map(shopClient -> dtoList.add(new UserDto(shopClient))).collect(Collectors.toList());
            return dtoList;
        }
        throw new RegistrationException(HttpStatus.NO_CONTENT, "Don't found client for your parameters!");
    }

    @Override
    @Transactional
    public UserDto findByMail(String mail) {
        if (clientRepository.findByMail(mail).isPresent()){
            return new UserDto(clientRepository.findByMail(mail).get());
        }
              return null;

    }
}
