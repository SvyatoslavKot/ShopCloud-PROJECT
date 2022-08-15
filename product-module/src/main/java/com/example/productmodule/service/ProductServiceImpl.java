package com.example.productmodule.service;

import com.example.productmodule.domain.Product;
import com.example.productmodule.repository.ProductRepository;
import com.example.productmodule.service.jpaSpecification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Page<Product> getByParams(Optional<String> nameFilter,
                                     Optional<Integer> page,
                                     Optional<Integer> size,
                                     Optional<BigDecimal> min,
                                     Optional<BigDecimal> max,
                                     Optional<String> sortField,
                                     Optional<String> sortOrder){
        /*
        if (!nameFilter.contains("%")){

            nameFilter = String.join("","%", nameFilter,"%");
        }
        return productRepository.findProdByTitleLike(nameFilter);
        */
        Specification<Product> specification = Specification.where(null);
        if(nameFilter.isPresent()){
            specification = specification.and(ProductSpecification.titleLike(nameFilter.get()));
        }
        if (min.isPresent()){
            specification = specification.and(ProductSpecification.greaterOrEquals(min.get()));
        }
        if (max.isPresent()){
            specification = specification.and(ProductSpecification.lessOrEquals(max.get()));
        }
        if (sortField.isPresent() && sortOrder.isPresent()){
            productRepository.findAll(specification,
                    PageRequest.of(page.orElse(1)-1,size.orElse(4),
                            Sort.by(Sort.Direction.fromString(sortOrder.get()), sortField.get())));
        }

        return productRepository.findAll(specification,
                PageRequest.of(page.orElse(1)-1,size.orElse(4)));
    }

}
