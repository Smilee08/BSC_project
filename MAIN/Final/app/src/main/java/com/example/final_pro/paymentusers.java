package com.example.final_pro;

public class paymentusers {
    String name,wing,flatno,maintenance;

    public paymentusers() {
    }

    public paymentusers(String name, String wing, String flatno) {
        this.name = name;
        this.wing = wing;
        this.flatno = flatno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

}
