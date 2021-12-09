package com.aasif.cl_hdcse_95_46;

public class Appoinment {
    private String id;
    private String name;
    private String date;
    private String price;

    public Appoinment(String id, String name, String date, String price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}