package com.company.models;

import java.util.ArrayList;

public class Movie {
    public int id;
    public String title;
    public int type;
    public ArrayList<Location> locations;
    public int quantity;

    public Movie(String title, int type, int quantity){
        this.title = title;
        this.type = type;
        this.locations = new java.util.ArrayList<>();
        this.quantity = quantity;
    }

    public Movie(int id, String title, int type, int quantity){
        this.id = id;
        this.title = title;
        this.type = type;
        this.locations = new java.util.ArrayList<>();
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(){
        this.quantity++;
    }

    public void decreaseQuantity(){
        this.quantity--;
    }
}

