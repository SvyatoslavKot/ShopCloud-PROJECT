package com.example.productmodule.app.repository;

import com.example.productmodule.app.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<Bucket> findBucketByUserMail(String mail);
}
