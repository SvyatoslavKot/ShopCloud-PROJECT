package com.example.productmodule.repository;

import com.example.productmodule.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<Bucket> findBucketByUserMail(String mail);
}
