package com.example.productmodule.app.controller;


import com.example.productmodule.app.domain.Product;
import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.service.ProductService;
import com.example.productmodule.app.dto.mappers.ProductMapperDTO;
import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

//swagger @Tag(name = "User Api", description = "API to manipulate user resource")
@RestController
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductService productService;
    private final ProductMapperDTO productMapperDTO;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
        this.productMapperDTO = new ProductMapperDTO();
    }


    @GetMapping(value = "/list", produces ="application/json")
    @Transactional
    public ResponseEntity<Page<Product>> getByParam(@RequestParam(name = "titleFilter", required = false) Optional<String> titleFilter,
                                    @RequestParam(name = "min", required = false) Optional<BigDecimal> min,
                                    @RequestParam(name = "max", required = false) Optional<BigDecimal> max,
                                    @RequestParam(name = "page", required = false) Optional<Integer> page,
                                    @RequestParam(name = "size", required = false) Optional<Integer> size,
                                    @RequestParam(name = "sortField", required = false) Optional<String> sortField,
                                    @RequestParam(name = "sortOrder", required = false) Optional<String> sortOrder){

        Page<Product> pageProduct = null;
        HttpHeaders headers = new HttpHeaders();

        try{
            pageProduct = productService.getByParams(
                    titleFilter,
                    page,
                    size,
                    min,
                    max,
                    sortField,
                    sortOrder);
        }catch (RuntimeException e) {
            headers.set("message", e.getMessage());
            return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
        }

            if (!pageProduct.getContent().isEmpty()){
                return new ResponseEntity(pageProduct, HttpStatus.OK);
            }
        headers.set("message", "Not found product by your param!");
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);

    }

    @GetMapping(produces ="application/json")
    @Transactional
    public ResponseEntity<ProductDTO> getById(@RequestParam(name = "id") Long id) {
        if(productService.getById(id).isPresent()){
            return  new ResponseEntity(new ProductDTO(
                    productService.getProductById(id)
            ), HttpStatus.OK);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("message", "Product with id: " + id +" not found!");
        return new ResponseEntity(new ProductDTO(), headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/add", produces ="application/json" , consumes = "application/json")
        public ResponseEntity<String> addProduct (@RequestBody ProductDTO productDTO) {
        try{
            if (productDTO.getId() != null) {
                if (productService.getById(productDTO.getId()).isPresent()) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("message", "Product with id: " + productDTO.getId() + " is present!");
                    return new ResponseEntity(headers, HttpStatus.RESET_CONTENT);
                }
            }
            if (productDTO.getPrice() == null || productDTO.getPrice().intValue() < 1 ) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("message", "Field price is Empty");
                return new ResponseEntity(headers, HttpStatus.PARTIAL_CONTENT);
            }
            if (productDTO.getTitle() == null || productDTO.getTitle().isEmpty() ) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("message", "Field title is Empty");
                return new ResponseEntity(headers, HttpStatus.PARTIAL_CONTENT);
            }

            productService.addProduct(productDTO);
            ResponseEntity response = new ResponseEntity<>(HttpStatus.OK);
            return response;


        }catch (RuntimeException e) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("message", e.getMessage());
            return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addToBucket/{productId}")
    public ResponseEntity addToBucket (@PathVariable("productId") Long productId, HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        var mail = req.getHeader("USER_MAIL");

        try {
            productService.addToUserBucket(productId, mail);
            return new ResponseEntity(HttpStatus.OK);
        }catch (RuntimeException e) {
            System.out.println("exception " + e.getMessage());
            headers.set("message", "Product with id: " + productId +" doesn't add to bucket " + mail);
            return new ResponseEntity(headers, HttpStatus.RESET_CONTENT);
        }
    }

    @PostMapping("/removeFromBucket")
    public ResponseEntity removeFromBucket(HttpServletRequest req,
                                           @RequestParam(name = "id" , required = false) Long id) {
        HttpHeaders headers = new HttpHeaders();
        if (id != null){
            var mail = req.getHeader("USER_MAIL");
           if (mail!= null && !mail.isEmpty()){
               try{
                   System.out.println(mail+ " " + id );
                   productService.removeFromBucket(id,mail);
                   return new ResponseEntity(HttpStatus.OK);
               }catch (RuntimeException e) {
                   headers.set("message", e.getMessage());
                   return new ResponseEntity(headers,HttpStatus.NO_CONTENT);
               }
           }
            headers.set("message", "Mail is impty ");
            return new ResponseEntity(headers, HttpStatus.RESET_CONTENT);
        }
        headers.set("message", "Id is impty ");
        return new ResponseEntity(headers, HttpStatus.RESET_CONTENT);

    }

}
