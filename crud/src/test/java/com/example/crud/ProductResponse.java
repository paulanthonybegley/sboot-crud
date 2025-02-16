package com.example.crud;

import java.util.UUID;

public class ProductResponse {

    private UUID id;

    ProductResponse(){}

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }

}
