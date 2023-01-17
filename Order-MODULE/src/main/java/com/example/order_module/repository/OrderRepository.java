package com.example.order_module.repository;

import com.example.order_module.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT u FROM Order u WHERE u.user_mail = :mail")
    List<Order> findAllByMailNatile(@Param("mail") String mail);
}
