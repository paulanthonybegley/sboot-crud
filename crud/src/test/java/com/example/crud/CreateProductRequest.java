package com.example.crud;

public class CreateProductRequest {
    private String name;

    CreateProductRequest(){}

    CreateProductRequest(String name){
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
