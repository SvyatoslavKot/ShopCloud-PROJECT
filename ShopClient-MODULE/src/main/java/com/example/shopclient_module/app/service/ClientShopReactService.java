package com.example.shopclient_module.app.service;

import com.example.shopclient_module.app.domain.ShopClient;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.repository.ClientShopReactRepository;
import com.example.shopclient_module.app.repository.ClientShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ClientShopReactService {


    private final ClientShopRepository repo;

    public ClientShopReactService( ClientShopRepository repo) {

        this.repo = repo;
    }

    public Mono<UserDto> findByMail(String mail) {
        UserDto userDto = new UserDto(repo.findByMail(mail).get());
        if (repo.findByMail(mail).isPresent()){
             userDto = new UserDto(repo.findByMail(mail).get());
            return Mono.just(userDto);
        }
       return null;

    }

}
