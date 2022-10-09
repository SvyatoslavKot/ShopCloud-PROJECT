package com.example.shopclient_module.rabbitMq.listener;

import com.example.shopclient_module.domain.Role;
import com.example.shopclient_module.domain.ShopClient;
import com.example.shopclient_module.domain.Status;
import com.example.shopclient_module.dto.UserDto;
import com.example.shopclient_module.exception.RegistrationException;
import com.example.shopclient_module.rabbitMq.producers.AuthProducer;
import com.example.shopclient_module.repository.ClientShopRepository;
import com.example.shopclient_module.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class ShopModuleListener {

    private ClientShopRepository repository;
    private final ClientService clientService;

    @RabbitListener(queues = "queue-shopclientNew")
    @Transactional
    public UserDto newClient(@Payload UserDto userDto) throws RegistrationException {
        return clientService.registrationClient(userDto);
    }

    @RabbitListener(queues = "queue-shopclientGetUser")
    @Transactional
    public UserDto getClient(@Payload String mail) throws InterruptedException{
        log.info("User email: " + mail);
        if (repository.findByMail(mail).isPresent()){
            return new UserDto(repository.findByMail(mail).get());
        }
        return null;//"Пользователь с таким @mail уже зарегмстрирован.";
    }

    @RabbitListener(queues = "queue-shopclientUpdateClient")
    public void updateClient(@Payload UserDto request){
        try{
            clientService.updateClient(request);
        }catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }
    }
}
