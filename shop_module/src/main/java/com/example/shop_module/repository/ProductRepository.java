package com.example.shop_module.repository;

import com.example.shop_module.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Long> {


}
