package com.example.authservice.app.mq.rabbitMq;

import com.example.authservice.app.service.UserService;
import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import com.example.authservice.app.requestBeans.UserRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@AllArgsConstructor
public class ShopclientListener {

   private final  UserRepository repository;
   private final UserService userService;
   private final PasswordEncoder encoder;

    @RabbitListener(queues = "queue-auth-new-client")
    @Transactional
    public void consume2(@Payload UserRequest request){
        if (!repository.findByMail(request.getMail()).isPresent()){
            log.info("method save -> " + request.getMail());
            request.setPassword(encoder.encode(request.getPassword()));
            User user = new User(request);
            repository.save(user);
        }
    }

    @RabbitListener(queues = "queue-shopclientUpdateClientAuth")
    public void updateClient(@Payload UserRequest request){
        try{
            userService.updateUser(request);
        }catch (RuntimeException e) {
            log.error(e.getMessage(),e.getCause());
            e.getStackTrace();
        }


    }
}
