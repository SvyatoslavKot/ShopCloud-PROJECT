package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.rabbitMq.RabbitMqSetting;
import com.example.shop_module.app.mq.rabbitMq.abstraction.RabbitProducing;
import com.example.shop_module.app.service.abstraction.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Slf4j
public class RabbitUserService extends RabbitAbstractService implements UserService {

    private RabbitProducing producer;

    public RabbitUserService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
        this.producer = getProducer();
    }

    @Override
    public ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException {

        log.info("Produce to ClientModule -> new Client, UserDTO: {}", userDTO);
        UserDTO response = (UserDTO) producer.convertSendAndReceive(RabbitMqSetting.EXCHANGE_SHOPCLIENT_NEW.getValue(),
                RabbitMqSetting.ROUTING_KEY_SHOPCLIENT_NEW.getValue(),
                userDTO,
                UserDTO.class);

        if (response != null){
            if (response.getMail() != null) {
                log.info("Produce to ClientModule return -> {} ", response);
                return new ResponseEntity (response, HttpStatus.OK);
            }
            return new ResponseEntity( "Uncorrect user data",HttpStatus.CONFLICT);
        }
        log.info("Order Module is not responding");
        return new ResponseEntity("Order Module is not responding", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity findByMail(String mail) {

        log.info("Produce to ClientModule -> get Client by mail, mail: {}", mail);
        UserDTO response = (UserDTO) producer.convertSendAndReceive(RabbitMqSetting.EXCHANGE_GET_CLIENT.getValue(),
                RabbitMqSetting.ROUTING_KEY_GET_CLIENT.getValue(),
                mail,
                UserDTO.class);

        if (response != null) {
            if (response.getMail() != null){
                log.info("Produce to ClientModule return -> {} ", response);
                return new ResponseEntity(response, HttpStatus.OK);
            }
            return new ResponseEntity("Client with mail <" + mail + "> not found!", HttpStatus.NOT_FOUND);
        }
        log.info("Order Module is not responding");
        return new ResponseEntity("Order Module is not responding", HttpStatus.SERVICE_UNAVAILABLE);

    }

    @Override
    public ResponseEntity findAllUserDto() {

        log.info("Produce to ClientModule -> find all Clients");
        List<UserDTO> userList = (List<UserDTO>) producer.convertSendAndReceiveList(RabbitMqSetting.EXCHANGE_FIND_ALL_CLIENT.getValue(),
                RabbitMqSetting.ROUTING_KEY_FIND_ALL_CLIENT.getValue(),
                null);
        if ( userList != null ){
            if ( !userList.isEmpty() ) {
                return new ResponseEntity(userList, HttpStatus.OK);
            }
            return new ResponseEntity("Clients not found", HttpStatus.NO_CONTENT);
        }
        log.info("Order Module is not responding");
        return new ResponseEntity("Order Module is not responding", HttpStatus.SERVICE_UNAVAILABLE);

    }

    @Override
    public void updateProfile(UserDTO userDTO) {

        log.info("Produce to ClientModule -> update Client, userDTO: {}", userDTO);
        producer.produce(RabbitMqSetting.EXCHANGE_UPDATE_CLIENT.getValue(), RabbitMqSetting.ROUTING_KEY_UPDATE_CLIENT.getValue(), userDTO);

    }
}
