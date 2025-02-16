package com.example.crud.model;

import java.util.UUID;

public class Product {

    private UUID id;
    private String name;

    public Product(UUID id, String name){
        super();
        setId(id);
        setName(name);
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName()
    {
        return name;
    }

    public static Product create(String name){
        return new Product(UUID.randomUUID(), name);
    }

}
