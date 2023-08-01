package com.example.final_pro;

public class Delivery_users {
    String name, time, date,typeofdelivery,recievername,flatno,contactno;

    public Delivery_users() {
    }

    public Delivery_users(String name, String time, String date, String typeofdelivery, String recievername, String flatno, String contactno) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.typeofdelivery = typeofdelivery;
        this.recievername = recievername;
        this.flatno = flatno;
        this.contactno = contactno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeofdelivery() {
        return typeofdelivery;
    }

    public void setTypeofdelivery(String typeofdelivery) {
        this.typeofdelivery = typeofdelivery;
    }

    public String getRecievername() {
        return recievername;
    }

    public void setRecievername(String recievername) {
        this.recievername = recievername;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }
}
