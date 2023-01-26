package com.example.shop_module.app.controller;

import com.example.shop_module.app.domain.enums.Role;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.mq.kafka.KafkaSettings;
import com.example.shop_module.app.mq.kafka.producer.KafkaProduceProductModule;
import com.example.shop_module.app.mq.kafka.producer.KafkaProducer;
import com.example.shop_module.app.service.ProductService;
import com.example.shop_module.app.service.UserService;
import com.example.shop_module.app.service.kafkaService.KafkaClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class KafkaController {

    @Autowired
    @Qualifier("kafkaProductService")
    private ProductService productService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaProduceProductModule kafkaProduceProductModule;

    @Autowired
    @Qualifier("kafkaClientService")
    private UserService userService;

    @GetMapping("/kafka/productAddToBucket")
    public String addToBucket () {
        productService.addToUserBucket(1l,"mail");
        return "send";
    }
    @GetMapping("/kafka/updateProduct")
    public String updateProduct () {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("new Product");
        productDTO.setPrice(new BigDecimal(22.33));
        productService.updateProduct(productDTO);
        return "send";
    }


    @GetMapping("/kafka/product/add")
    public String addProduct (){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("new Product");
        productDTO.setPrice(new BigDecimal(22.33));

        productService.addProduct(productDTO);

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
            kafkaProducer.produce(KafkaSettings.TOPIC_CLIENT_MODULE_MESSAGE.getValue(), userDTO);
            return "User -> " + userDTO + " add success!";
        }catch (Exception e) {
            return e.getMessage();
        }
    }
}
