package com.example.productmodule.service.jpaSpecification;

import com.example.productmodule.domain.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> titleLike(String titleFilter){
        return (root,query,builder) -> builder.like(root.get("title"),"%"+titleFilter+"%");
    }
    public static Specification<Product> greaterOrEquals (BigDecimal min){
        return (root,query,builder) -> builder.greaterThanOrEqualTo(root.get("price"),min);
    }
    public static Specification<Product> lessOrEquals (BigDecimal max){
        return (root,query,builder) -> builder.lessThanOrEqualTo(root.get("price"),max);
    }
}
