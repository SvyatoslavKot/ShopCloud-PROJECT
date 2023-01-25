package com.example.productmodule.app.controller;

import com.example.productmodule.app.service.documentSevice.DocumentServiceTXT;
import com.example.productmodule.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
public class ProductDocumentController {

    private final ProductService productService;
    private final DocumentServiceTXT documentService;

    public ProductDocumentController(ProductService productService, DocumentServiceTXT documentService) {
        this.productService = productService;
        this.documentService = documentService;
    }

    @RequestMapping(value = "/add/fromFile", method = RequestMethod.POST)
    public void handleFileUpload(@RequestBody Map<String, byte[]> requestMap) {

        String name = new String(requestMap.get("name"), StandardCharsets.UTF_8);
        byte[] bytes = requestMap.get("file");

        documentService.SaveFromDocument(name, bytes);
    }
}
