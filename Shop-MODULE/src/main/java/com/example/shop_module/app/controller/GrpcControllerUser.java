package com.example.shop_module.app.controller;

import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GrpcControllerUser {

    @Autowired
    @Qualifier("grpcUserService")
    private UserService userService;

    @GetMapping("/grpc/user/findbymail/{mail}")
    public String findByMail(@PathVariable("mail") String mail) {
        try{
            ResponseEntity responseEntity = userService.findByMail(mail);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                UserDTO user = (UserDTO) responseEntity.getBody();
                return user.toString();
            }
            var msg = (String) responseEntity.getBody();

            return msg;
        }catch (NoConnectedToGRpsServer e){
            System.out.println("Controller Exception");
            throw new NoConnectedToGRpsServer();
        }
    }

    @GetMapping("/grpc/user/reg")
    public String registration () {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("name");
        userDTO.setLastName("lastname");
        userDTO.setMail("mail");
        userDTO.setPassword("1111");
        userDTO.setMatchingPassword("1111");

        ResponseEntity responseEntity = userService.registrationClient(userDTO);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            var msg = "Success";
            return msg;
        }else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)){
            throw new RuntimeException((String)responseEntity.getBody());
        }
        var msg = (String) responseEntity.getBody();
        return msg;
    }

    @GetMapping("/grpc/user/all")
    public String getAll () {
        ResponseEntity responseEntity = userService.findAllUserDto();

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            List<UserDTO> userList = (List<UserDTO>) responseEntity.getBody();
            return userList.toString();
        }else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
            throw  new RuntimeException((String)responseEntity.getBody());
        }

        var msg = (String) responseEntity.getBody();
        return msg;
    }
}
