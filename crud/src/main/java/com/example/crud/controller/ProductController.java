package com.example.crud.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.model.Product;

@RestController
public class ProductController {
    @PostMapping("/products")
    ResponseEntity<Product> createNewProduct(){
        return ResponseEntity.created(null).body(new Product(UUID.randomUUID()));
    }
    
}
