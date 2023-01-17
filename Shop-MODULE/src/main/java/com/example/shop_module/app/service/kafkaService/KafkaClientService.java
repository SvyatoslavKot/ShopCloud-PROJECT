package com.example.shop_module.app.service.kafkaService;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.mq.ProduceShopClient;
import com.example.shop_module.app.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class KafkaClientService implements UserService {

    private final ProduceShopClient produceShopClient;

    public KafkaClientService(@Qualifier("kafkaProduceClientModule") ProduceShopClient produceShopClient) {
        this.produceShopClient = produceShopClient;
    }

    @Override
    public ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException {
        return null;
    }

    @Override
    public ResponseEntity findByMail(String mail) {
        try{
            UserDTO user = produceShopClient.getClientByMail(mail);
            return new ResponseEntity(user, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity findAllUserDto() {
        return null;
    }

    @Override
    public void updateProfile(UserDTO userDTO) {

            produceShopClient.updateClient(userDTO);

    }
}
