package com.aasif.cl_hdcse_95_46;

public class Appoinment {
    private String serviceName;
    private int date;
    private int time;

    public Appoinment(String serviceName, int date, int time) {
        this.serviceName = serviceName;
        this.date = date;
        this.time = time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
