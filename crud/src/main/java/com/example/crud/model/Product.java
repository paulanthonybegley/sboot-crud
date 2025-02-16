package com.example.crud.model;

import java.util.UUID;

public class Product {

    private UUID id;

    public Product(UUID id){
        setId(id);
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }

    public static Product create(){
        return new Product(UUID.randomUUID());
    }

}
