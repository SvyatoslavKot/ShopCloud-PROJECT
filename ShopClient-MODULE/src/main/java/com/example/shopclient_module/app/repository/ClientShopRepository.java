package com.example.shopclient_module.app.repository;

import com.example.shopclient_module.app.domain.ShopClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ClientShopRepository extends JpaRepository<ShopClient, Long> {

     Optional<ShopClient> findByMail(String email);
}
