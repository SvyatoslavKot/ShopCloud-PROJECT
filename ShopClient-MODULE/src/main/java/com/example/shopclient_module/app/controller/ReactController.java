package com.example.shopclient_module.app.controller;

import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.service.ClientService;
import com.example.shopclient_module.app.service.ClientShopReactService;
import io.swagger.annotations.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/react/client")
@Api(value = "ClientShop", description = "React Controller for ClientShop operation", tags = "ClientShop react-Controller API")
public class ReactController {

    private final ClientShopReactService reactService;
    private final ClientService clientService;

    public ReactController(ClientShopReactService reactService, ClientService clientService) {
        this.reactService = reactService;
        this.clientService = clientService;
    }




    @GetMapping(value = "/getByMail/{mail}", produces = "application/json")
    @ApiOperation(value = "Returned ClientShop by mail from DB client-shop")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 205, message = "Reset content" ),
            @ApiResponse(code = 204, message = "No Content")
    })
    public Mono<ResponseEntity<UserDto>> getByMail(@PathVariable("mail") String mail) {
        HttpHeaders headers = new HttpHeaders();
        UserDto userDto = new UserDto();
        userDto.setMail("");
        userDto.setPassword("");
        userDto.setFirstName("");
        try{
            if (clientService.findByMail(mail) != null) {
                UserDto user = clientService.findByMail(mail);
                ResponseEntity<UserDto> response =  new ResponseEntity<UserDto>(user, HttpStatus.OK);
                System.out.println("find -> " + response);
                return Mono.just(response);
            }
            headers.set("message" , "User with mail: " + mail + " not found!");
             ResponseEntity<UserDto> response = new ResponseEntity<UserDto>(userDto, headers, HttpStatus.RESET_CONTENT);
            System.out.println("null -> " + response);
            return Mono.just(response);


        }catch (RuntimeException e) {
            System.out.println(e);
            headers.set("message", e.getMessage());
            ResponseEntity<UserDto>  response = new ResponseEntity<UserDto> (userDto, headers, HttpStatus.NO_CONTENT);
            System.out.println("exception -> " + response);
            return Mono.just(response);
        }
    }
}
