package com.example.final_pro;

public class Guestusers {
    String name,wing,vehicalno,vehicaltype,timein,timeout;

    public Guestusers() {
    }

    public Guestusers(String name, String wing, String vehicalno, String vehicaltype, String timein, String timeout) {
        this.name = name;
        this.wing = wing;
        this.vehicalno = vehicalno;
        this.vehicaltype = vehicaltype;
        this.timein = timein;
        this.timeout = timeout;
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

    public String getVehicalno() {
        return vehicalno;
    }

    public void setVehicalno(String vehicalno) {
        this.vehicalno = vehicalno;
    }

    public String getVehicaltype() {
        return vehicaltype;
    }

    public void setVehicaltype(String vehicaltype) {
        this.vehicaltype = vehicaltype;
    }

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
