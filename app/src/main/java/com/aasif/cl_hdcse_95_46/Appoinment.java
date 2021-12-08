package com.aasif.cl_hdcse_95_46;

public class Appoinment {
    private String id;
    private String name;
    private String date;
    private String time;
    private String price;

    public Appoinment(String id, String name, String date, String time, String price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
