package com.example.lks2024.model;

public class Delivery {
    private int id;
    private int price;
    private int duration;
    private String name;

    public Delivery(int id, int price, int duration, String name) {
        this.id = id;
        this.price = price;
        this.duration = duration;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

//"id": 1,
//        "name": "Fast Delivery",
//        "duration": 2,
//        "price": 12000,
//        "transactions": []