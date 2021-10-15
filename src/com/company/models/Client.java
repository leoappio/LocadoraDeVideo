package com.company.models;

public class Client {
    public int id;
    public String name;
    public String phone;


    public Client(String name, String phone){
        this.name = name;
        this.phone = phone;
    }
    public Client(int id,String name, String phone){
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

}
