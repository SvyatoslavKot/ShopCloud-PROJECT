package com.example.shop_module.controller;

import com.example.shop_module.aopService.MeasureMethod;
import com.example.shop_module.domain.Product;
import com.example.shop_module.dto.ProductDTO;
import com.example.shop_module.service.ProductService;
import com.example.shop_module.service.SessionObjectHolder;
import com.example.shop_module.wrapper.PageableResponse;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;
    private final RestTemplate restTemplate;
    private static final String PRODUCT_URL= "http://localhost:8083/api/v1/product";

    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder, RestTemplate restTemplate) {
        this.productService = productService;
        this.sessionObjectHolder = sessionObjectHolder;
        this.restTemplate = restTemplate;
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

        ResponseEntity<PageableResponse<Product>> exchangeProductList = restTemplate
                .exchange(PRODUCT_URL + "/list?page="+page.orElse(1)  +"&size="+size.orElse(4)
                                +"&titleFilter="+titleFilter.orElse("")   +"&min="+min.orElse(BigDecimal.valueOf(0))
                                +"&max="+max.orElse(BigDecimal.valueOf(99999*9)),
                        HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Product>>() {});
        List<Product> products = exchangeProductList.getBody().getContent();
        model.addAttribute("products",exchangeProductList.getBody());
        model.addAttribute("list", products);
        return "product_page/products";
    }

    @MeasureMethod
    @GetMapping
    public String list (Model model){
        sessionObjectHolder.addClick();
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "product_page/productTable";
    }

    @GetMapping("/item/{product_id}")
    public String getProductById(Model model, @PathVariable("product_id")Long id){
        ResponseEntity<ProductDTO> responseItem = restTemplate
                .getForEntity(PRODUCT_URL + "/item/productId/"+ id  , ProductDTO.class);
        JSONObject response = new JSONObject(responseItem.getBody());
       // System.out.println(response);
        ProductDTO product = (ProductDTO) responseItem.getBody();
        System.out.println(responseItem.getBody());
        model.addAttribute("product", product);
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


    @GetMapping("/{id}/bucket/remove")
    public String removeFromBucket(@PathVariable Long id, Principal principal){
        if (principal == null){
            return "redirect:/bucket";
        }
        productService.removeFromBucket(id, principal.getName());
        return "redirect:/bucket";
    }
}
