package com.example.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @PostMapping("/products")
    ResponseEntity<String> createNewProduct(){
        return ResponseEntity.created(null).body("");
    }
    
}
