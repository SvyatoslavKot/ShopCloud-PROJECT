package com.example.order_module.repository;

import com.example.order_module.domain.OrdersDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrdersDetails, Long> {
}
