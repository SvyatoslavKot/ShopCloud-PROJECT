package com.example.shopclient_module.app.mq.kafka;

import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.exception.RegistrationException;
import com.example.shopclient_module.app.mq.ShopModuleListener;
import com.example.shopclient_module.app.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@Slf4j
public class KafkaShopModuleListener  {


    @Autowired
    private ClientService clientService;
    @Autowired
    private ObjectMapper objectMapper;


    private final String REQUEST_TOPIC = KafkaSettings.TOPIC_CLIENT_MODULE_GET_CLIENT_BY_MAIL.getValue();

    public UserDto newClient(UserDto userDto) throws RegistrationException {
        try{
            UserDto user =  clientService.registrationClient(userDto);
            return user;
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return null;
        }
    }

   // @KafkaListener(topics = "client-m_message-request" , containerFactory = "requestListenerContainerFactory")
    //@SendTo()
    public UserDto getClient(String mail) throws InterruptedException {
        log.info("request -> {}", mail);
        UserDto userDto = clientService.findByMail(mail);
        String reply = null;
        try {
            reply = objectMapper.writeValueAsString(userDto);
            log.info("reply -> {}", userDto);
            return userDto;// TODO: 011 11.12.22 change return object pojo
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<UserDto> findAllClient() {
        return null;
    }

    @KafkaListener(topics = "client-m_update-client", containerFactory = "kafkaListenerContainerFactoryUser")
    public void updateClient(UserDto userDto)  {
        log.info("UserDto -> {}", userDto.getMail());
        clientService.updateClient(userDto);

    }

    @KafkaListener(topics = "client-m_message", containerFactory = "kafkaListenerContainerFactoryUser")
    public void messageShopModule(@Payload UserDto o) {
        log.info("message request -> {}", o.getMail());
    }

    @KafkaListener(topics = "client-m_get-client-by-mail", containerFactory = "kafkaListenerContainerFactoryString")
    public void getByMail(String request) throws JSONException {
        log.info("get by mail ---> {}", request);

    }


}
