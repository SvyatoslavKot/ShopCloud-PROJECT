package com.example.shop_module.app.service.restService;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.NoConnectedToRestService;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.service.abstraction.UserService;
import com.example.shop_module.app.service.restService.abstraction.WebClientAbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientClientShopService extends WebClientAbstractService implements UserService {

    private String REACT_GET_CLIENT_BY_MAIL = "/react/client/getByMail/";
    ResponseEntity<UserDTO> response;

    public WebClientClientShopService(WebClient.Builder webBuilder, HttpClientSettings httpClientSettings) {
        super(webBuilder, httpClientSettings);
    }

    @Override
    public ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException {
        return null;
    }

    @Override
    public ResponseEntity findByMail(String mail) {
        String url = REACT_GET_CLIENT_BY_MAIL + mail;

        try{
            response = requester.performRequest(url, UserDTO.class).block();
        }catch (NoConnectedToRestService e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }

        if (response != null){
            if (response.getStatusCode().equals(HttpStatus.OK)){
                return response;
            }
            var msg = response.getHeaders().get("message").stream().findFirst().get();
            return new ResponseEntity(msg, response.getStatusCode());
        }
        return null;
    }

    @Override
    public ResponseEntity findAllUserDto() {
        return null;
    }

    @Override
    public void updateProfile(UserDTO userDTO) {

    }
}
