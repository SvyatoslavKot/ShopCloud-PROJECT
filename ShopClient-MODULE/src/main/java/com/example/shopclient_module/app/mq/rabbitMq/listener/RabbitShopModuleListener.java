package com.example.shopclient_module.app.mq.rabbitMq.listener;

import com.example.shopclient_module.app.mq.ShopModuleListener;
import com.example.shopclient_module.app.exception.RegistrationException;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitShopModuleListener implements ShopModuleListener {

    private final ClientShopRepository repository;
    private final ClientService clientService;

    @RabbitListener(queues = "queue-shopclientNew")
    @Transactional
    @Override
    public UserDto newClient(@Payload UserDto userDto) throws RegistrationException {
       try{
           UserDto responseClient = clientService.registrationClient(userDto);
           return responseClient;
       }catch (RegistrationException e){
           log.error(e.getMessage(),e.getCause());
           return new UserDto();
       }
    }

    @RabbitListener(queues = "queue-shopclientGetUser")
    @Transactional
    @Override
    public UserDto getClient(@Payload String mail) {
        log.info("User email: " + mail);
        if (repository.findByMail(mail).isPresent()){
            return new UserDto(repository.findByMail(mail).get());
        }
        return null;//"Пользователь с таким @mail уже зарегмстрирован.";
    }

    @RabbitListener(queues = "queue-shopclientUpdateClient")
    @Override
    public void updateClient(@Payload UserDto request){
        try{
            clientService.updateClient(request);
        }catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "queue-shop_client-find-all")
    @Override
    public List<UserDto> findAllClient(){
        try{
            return clientService.findAllClient();
        }catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            e.printStackTrace();
            return null;
        }

    }
}
