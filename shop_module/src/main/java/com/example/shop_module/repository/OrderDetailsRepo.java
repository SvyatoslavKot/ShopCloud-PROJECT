package com.example.shop_module.repository;

import com.example.shop_module.domain.OrdersDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrdersDetails, Long> {
}
