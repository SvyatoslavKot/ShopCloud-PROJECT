package com.example.shop_module.repository;

import com.example.shop_module.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
}
