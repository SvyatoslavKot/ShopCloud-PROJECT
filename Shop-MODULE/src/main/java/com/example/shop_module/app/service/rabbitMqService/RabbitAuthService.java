package com.example.shop_module.app.service.rabbitMqService;

import com.example.shop_module.app.exceptions.LoginClientException;
import com.example.shop_module.app.mq.ProduserAuthModule;
import com.example.shop_module.app.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Map;

@Service
@Slf4j
public class RabbitAuthService implements AuthService {

    @Autowired
    @Qualifier("rabbitProduceAuthModule")
    private ProduserAuthModule rabbitProduceAuth;

    @Override
    public ResponseEntity authorization(String email, String password) throws RuntimeException {
            Map<Object, Object> responseMap = rabbitProduceAuth.authorization(email,password);

            if(responseMap.get(HttpStatus.OK.name()) != null){
                return new ResponseEntity(responseMap.get(HttpStatus.OK.name()), HttpStatus.OK);
            }
            throw new LoginClientException(HttpStatus.NON_AUTHORITATIVE_INFORMATION,
                    (String)responseMap.get(HttpStatus.NON_AUTHORITATIVE_INFORMATION.name()));
        }
}
