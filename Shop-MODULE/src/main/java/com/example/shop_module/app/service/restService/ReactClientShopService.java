package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.restClient.ClientShopWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReactClientShopService {

    private final ClientShopWebClient webClient;

    private String URL = "http://localhost:8080/api/v1/react/client";

    public ReactClientShopService(ClientShopWebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<UserDTO> getByMail (String mail) {
            ResponseEntity<UserDTO> response = null;
            try{
                response = webClient.performRequest(URL+ "/getByMail/" + mail).block();
                log.info(response.toString());
            }catch (Exception e ){
                log.info(e.getMessage());
                throw new ResponseMessageException(response.getStatusCode(), e.getMessage());
            }

            if (response != null){
                if (response.getStatusCode().equals(HttpStatus.OK)){
                    return response;
                }
            }
            var msg = response.getHeaders().get("message").stream().findFirst().get();
            throw new ResponseMessageException(response.getStatusCode(), msg);


    }
}
