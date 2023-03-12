package com.example.shop_module.app.controller;

import com.example.shop_module.app.domain.enums.Role;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.service.abstraction.ProductService;
import com.example.shop_module.app.service.ServiceFactory;
import com.example.shop_module.app.service.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class KafkaController {

    private final ProductService kafkaProductService;
    private final UserService userService;

    public KafkaController(@Qualifier("myKafkaTemplate") KafkaTemplate kafkaTemplate) {
        this.kafkaProductService = ServiceFactory.newProductService(kafkaTemplate);
        this.userService = ServiceFactory.newUserService(kafkaTemplate);
    }

    @GetMapping("/kafka/productAddToBucket")
    public String addToBucket () {
        kafkaProductService.addToUserBucket(1l,"mail");
        return "send";
    }
    @GetMapping("/kafka/updateProduct")
    public String updateProduct () {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("new Product");
        productDTO.setPrice(new BigDecimal(22.33));
        kafkaProductService.updateProduct(productDTO);
        return "send";
    }


    @GetMapping("/kafka/product/add")
    public String addProduct (){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("new Product");
        productDTO.setPrice(new BigDecimal(22.33));

        kafkaProductService.addProduct(productDTO);

        return "Product add";
    }


    @GetMapping("/kafka/client/update/{mail}")
    public String updateClient(@PathVariable("mail") String mail) {
        UserDTO userDTO = UserDTO.builder()
                .firstName("firstName")
                .lastName("lls333ls")
                .mail("mail_mail@mail.ru")
                .role(Role.USER)
                .build();
        try{
            userService.updateProfile(userDTO);
            return "User -> " + userDTO + " add success!";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/kafka/client/message")
    public String updateClient() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("firstName")
                .lastName("llsls")
                .mail("mail_mail@mail.ru")
                .bucket_id(6l)
                .role(Role.USER)
                .build();
        try{
            //kafkaProducer.produce(KafkaSettings.TOPIC_CLIENT_MODULE_MESSAGE.getValue(), userDTO);
            return "User -> " + userDTO + " add success!";
        }catch (Exception e) {
            return e.getMessage();
        }
    }
}
