package com.example.final_pro;

public class Vendor_detailusers {
    String name, type,days,contactno;

    public Vendor_detailusers() {
    }

    public Vendor_detailusers(String name, String type, String days, String contactno) {
        this.name = name;
        this.type = type;
        this.days = days;
        this.contactno = contactno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }
}
