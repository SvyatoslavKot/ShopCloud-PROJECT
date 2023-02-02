package com.example.shop_module.app.controller;

import com.example.shop_module.app.aopService.MeasureMethod;
import com.example.shop_module.app.restClient.RestProductClient;
import com.example.shop_module.app.service.ProductService;
import com.example.shop_module.app.service.SessionObjectHolder;
import com.example.shop_module.app.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductService restProductService;
    private final ProductService grpcProductService;
    private final SessionObjectHolder sessionObjectHolder;
    private final RestProductClient restProductClient;

    public ProductController(@Qualifier("rabbitProductService") ProductService productService,
                             SessionObjectHolder sessionObjectHolder,
                             @Qualifier("restProductService") ProductService restProductService,
                             @Qualifier("grpcProductService") ProductService grpcProductService, RestProductClient restProductClient) {
        this.productService = productService;
        this.restProductService = restProductService;
        this.grpcProductService = grpcProductService;
        this.sessionObjectHolder = sessionObjectHolder;
        this.restProductClient = restProductClient;
    }

    @GetMapping("/list")
    public String getByParam(Model model,
                             @RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                             @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                             @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                             @RequestParam(name = "page", required = false) Optional<Integer> page,
                             @RequestParam(name = "size", required = false) Optional<Integer> size,
                             @RequestParam(name = "sortField", required = false) Optional<String> sortField,
                             @RequestParam(name = "sortOrder", required = false) Optional<String> sortOrder){

        var responseEntity = restProductService.getByParam(page,size,titleFilter, min,max);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            var products = (Page<ProductDTO>) responseEntity.getBody();
            var productList = products.getContent();
            model.addAttribute("list", productList);
            model.addAttribute("products",products);

            return "product_page/products";
        }else {
            String msg = (String) responseEntity.getBody();
            System.out.println(msg);model.addAttribute("msg", msg);
            return "product_page/products";
        }
    }

    @MeasureMethod
    @GetMapping
    public String list (Model model){
        sessionObjectHolder.addClick();
                var responseEntity = productService.getAll();
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                    var productList = (List<ProductDTO>) responseEntity.getBody();
                    model.addAttribute("products", productList);
                    return "product_page/productTable";
                }
                var exMsg = responseEntity.getBody().toString().split("\"")[1];
                model.addAttribute("msg", exMsg);
                return "product_page/productTable";

    }


    @GetMapping("/item/{product_id}")
    public String getProductById(Model model, @PathVariable("product_id")Long id)  {

            ResponseEntity response = productService.getById(id);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("response ok ->" + response.getBody());
                ProductDTO dto = (ProductDTO) response.getBody();
                model.addAttribute("product", dto);
                return "product_page/product_item";
            }
            String exceptionMsg = response.getBody().toString().split("\"")[1];
            model.addAttribute("product", new ProductDTO());
            model.addAttribute("msg", exceptionMsg);
            return "product_page/product_item";
        }

    @MeasureMethod
    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            log.info("User not authorize");
            return "redirect:/product/list";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/product/list";
    }

    @PostMapping
    ResponseEntity<Void> addProduct(ProductDTO productDTO){
        productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @MessageMapping("/products")
    public void messageAddProduct(ProductDTO dto) {
        productService.addProduct(dto);
    }

    @MessageMapping("/products2")
    public void messageAddProduct2(ProductDTO dto) {
        System.out.println(dto.getId());
    }

    @MessageMapping("/addToBucket")
    public void messageAddProductToBucket(ProductDTO dto) {
        productService.addToUserBucket(dto.getId(), "mail");
        System.out.println("socket method");
    }



    @PostMapping("/list")
    ResponseEntity<Void> addToBucket(ProductDTO dto){
        productService.addToUserBucket(dto.getId(),"mail");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @MessageMapping("/addBucket")
    public void messageAddToBucket(String mail, Long id) {
        productService.addToUserBucket(id,mail);
    }



}
