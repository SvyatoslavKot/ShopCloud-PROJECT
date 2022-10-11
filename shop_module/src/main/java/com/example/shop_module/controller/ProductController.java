package com.example.shop_module.controller;

import com.example.shop_module.aopService.MeasureMethod;
import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.service.ProductService;
import com.example.shop_module.service.SessionObjectHolder;
import com.example.shop_module.wrapper.PageableResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;

    @GetMapping("/list")
    public String getByParam(Model model,
                             @RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                             @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                             @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                             @RequestParam(name = "page", required = false) Optional<Integer> page,
                             @RequestParam(name = "size", required = false) Optional<Integer> size,
                             @RequestParam(name = "sortField", required = false) Optional<String> sortField,
                             @RequestParam(name = "sortOrder", required = false) Optional<String> sortOrder){

        var responseProducts = productService.getByParam(page.orElse(1),size.orElse(4),titleFilter.orElse(""),
                min.orElse(BigDecimal.valueOf(0)),max.orElse(BigDecimal.valueOf(99999*9)));

        if (responseProducts.getStatusCode().equals(HttpStatus.OK)){
            var products = (ResponseEntity<PageableResponse<ProductDTO>>) responseProducts.getBody();
            var productList = products.getBody().getContent();
            System.out.println(products);

            model.addAttribute("list", productList);
            model.addAttribute("products",products.getBody());
            return "product_page/products";
        }
        var exMsg = (String) responseProducts.getBody();
        model.addAttribute("msg", exMsg);
        return "product_page/products";

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
            return "redirect:/product";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/product";
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



}
