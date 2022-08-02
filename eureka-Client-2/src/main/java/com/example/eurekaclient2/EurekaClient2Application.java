package com.example.eurekaclient2;

import com.example.eurekaclient2.model.User;
import com.example.eurekaclient2.repository.UserReposiroty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient2Application.class, args);
    }

    @Bean
    public CommandLineRunner preload(UserReposiroty userReposiroty){
        return args -> {
            userReposiroty.save(new User("Ivan", "Ivanov", 24));
            userReposiroty.save(new User("Vasily", "Vasilyev", 25));
            userReposiroty.save(new User("Maxim", "Maximov", 27));
            userReposiroty.save(new User("Petr", "Petrov", 33));



        };
    }

}
