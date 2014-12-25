package com.homeinns.web.entity;

/**
 * Created by Administrator on 2014/12/25.
 */
public class Product {
    private long id;
    private String name;
    private int price;
    public Product() {
    }
    public Product(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
