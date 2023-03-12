package com.example.shop_module.app.controller;

import com.example.shop_module.app.domain.Category;
import com.example.shop_module.app.dto.ProductDTO;
import com.example.shop_module.app.restClient.HttpClientSettings;
import com.example.shop_module.app.service.ServiceFactory;
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
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RestController
public class RestProductController {

    private final ProductService restProductService;

    public RestProductController(RestTemplate restTemplate, HttpClientSettings httpClientSettings) {
        this.restProductService = ServiceFactory.newProductService(restTemplate,httpClientSettings);
    }

    @GetMapping("/rest/product/{id}")
    public String getById(@PathVariable("id") Long id) {
        ResponseEntity responseEntity = restProductService.getById(id);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            ProductDTO productDTO = (ProductDTO) responseEntity.getBody();
            return productDTO.toString();
        }
        String msg = (String) responseEntity.getBody();
        return msg;
    }

    @GetMapping("/rest/product/add")
    public String addProduct () {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("Apple Juice");
        productDTO.setPrice(new BigDecimal("101.99"));
        productDTO.setCategories(Collections.singletonList(new Category(5l, "Drink")));

        try{
            restProductService.addProduct(productDTO);
            return "Add new Product -> " + productDTO;
        }catch (RuntimeException e) {

            return e.getMessage();
        }
    }

    @GetMapping("/rest/getAllProduct")
    public String getAll(
                          @RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                          @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                          @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                          @RequestParam(name = "page", required = false) Optional<Integer> page,
                          @RequestParam(name = "size", required = false) Optional<Integer> size,
                          @RequestParam(name = "sortField", required = false) Optional<String> sortField,
                          @RequestParam(name = "sortOrder", required = false) Optional<String> sortOrder) {

        ResponseEntity responseEntity =  restProductService.getByParam(page, size, titleFilter, min, max);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            Page<ProductDTO> productPage = (Page<ProductDTO>) responseEntity.getBody();
            return productPage.getContent().toString();
        }else {
            String msg = (String) responseEntity.getBody();
            System.out.println(msg);
            throw new RuntimeException(msg);
        }
    }

    @GetMapping("/rest/Product/addBucket/{id}")
    public String addToBucket(@PathVariable("id") long id, Principal principal){

        String mail = (principal!= null) ? principal.getName() : SecurityContextHolder.getContext().getAuthentication().getName();

        try{
            restProductService.addToUserBucket(id,mail);
            return mail + " add to Bucket product with id " + id;
        }catch (RuntimeException e) {
            return  e.getMessage();
        }
    }



    @GetMapping("/rest/product/bucket/remove/{id}")
    public String productId (@PathVariable("id") Long id){
        var mail = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            restProductService.removeFromBucket(id, mail);
            return "success";
        }catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
