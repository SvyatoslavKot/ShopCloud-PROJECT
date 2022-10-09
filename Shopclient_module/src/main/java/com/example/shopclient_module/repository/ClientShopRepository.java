package com.example.shopclient_module.repository;

import com.example.shopclient_module.domain.ShopClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientShopRepository extends JpaRepository<ShopClient, Long> {

     Optional<ShopClient> findByMail(String email);
}
