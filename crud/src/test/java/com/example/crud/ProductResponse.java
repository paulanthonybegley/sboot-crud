package com.example.crud;

import java.util.UUID;

public class ProductResponse {

    private UUID id;
    private String name;

    ProductResponse(){}

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
