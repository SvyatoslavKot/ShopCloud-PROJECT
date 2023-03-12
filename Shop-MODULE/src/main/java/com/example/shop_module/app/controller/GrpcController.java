package com.example.shop_module.app.controller;


import com.example.shop_module.app.domain.Category;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.service.abstraction.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RestController
public class GrpcController {

    @Autowired
    @Qualifier("grpcProductService")
    private ProductService productService;

    @GetMapping("/grpc/product")
    public String productId (){

        ResponseEntity responseEntity = productService.getById(1l);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            ProductDTO productDTO = (ProductDTO) responseEntity.getBody();
            return productDTO.getTitle();
        }
        String msg = (String) responseEntity.getBody();
        throw new RuntimeException(msg);
    }

    @GetMapping("/grpc/product/{id}")
    public String productId (@PathVariable("id") long id){
        try{
            ResponseEntity responseEntity = productService.getById(id);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                ProductDTO productDTO = (ProductDTO) responseEntity.getBody();
                return productDTO.getTitle();
            }
            String msg = (String) responseEntity.getBody();
            System.out.println(msg);
            return msg;
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/grpc/product/remove/{id}")
    public String productId (@PathVariable("id") Long id){
        try{
            productService.removeFromBucket(id, SecurityContextHolder.getContext().getAuthentication().getName());
            return "success";
        }catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    @GetMapping("/grpc/getAllProduct/{page_req}")
    public String getAll( @PathVariable("page_req") int page_req,
                          @RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                          @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                          @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                          @RequestParam(name = "page", required = false) Optional<Integer> page,
                          @RequestParam(name = "size", required = false) Optional<Integer> size,
                          @RequestParam(name = "sortField", required = false) Optional<String> sortField,
                          @RequestParam(name = "sortOrder", required = false) Optional<String> sortOrder) {
        page = Optional.of(page_req);


            ResponseEntity responseEntity =  productService.getByParam(page, size, titleFilter, min, max);

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                Page<ProductDTO> productPage = (Page<ProductDTO>) responseEntity.getBody();
                return productPage.getContent().toString();
            }else {
                String msg = (String) responseEntity.getBody();
                System.out.println(msg);
                throw new RuntimeException(msg);
            }


    }

    @GetMapping("/grpc/Product/addBucket/{id}")
    public String addToBucket(@PathVariable("id") long id, Principal principal){

        String mail = (principal!= null) ? principal.getName() : SecurityContextHolder.getContext().getAuthentication().getName();

        try{
            productService.addToUserBucket(id,mail);
            return principal.getName() + " add to Bucket product with id " + id;
        }catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/grpc/prodct/add/prdocut")
    public String addProduct () {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("fanta");
        productDTO.setId(7l);
        productDTO.setPrice(new BigDecimal("81.99"));
        productDTO.setCategories(Collections.singletonList(new Category(5l, "Drink")));

        try{
            productService.addProduct(productDTO);
            return "Add ne Product -> " + productDTO;
        }catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
