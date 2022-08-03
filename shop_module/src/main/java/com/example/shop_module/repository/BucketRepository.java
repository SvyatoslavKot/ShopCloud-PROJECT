package com.example.shop_module.repository;

import com.example.shop_module.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;

public interface BucketRepository extends JpaRepository<Bucket,Long> {
}
