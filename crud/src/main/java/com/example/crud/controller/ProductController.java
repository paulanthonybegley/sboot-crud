package com.example.crud.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.crud.model.Product;

@RestController
public class ProductController {
    @PostMapping("/products")
    ResponseEntity<Product> createNewProduct(){
        Product newProduct = Product.create();
        URI newProductLocation = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(newProduct.getId())
                        .toUri();
                        
        return ResponseEntity.created(newProductLocation).body(newProduct);
    }
    
}
