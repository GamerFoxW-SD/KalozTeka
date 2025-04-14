package com.example.kalozteka.models;

public class UserModel {
    private String nev;
    private String id;

    public UserModel(String nev,String id){
        this.nev=nev;
        this.id=id;
    }

    public String getNev(){
        return this.nev;
    }

    public String getId() {
        return id;
    }
}
