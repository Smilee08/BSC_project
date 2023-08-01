package com.example.final_pro;

public class Vendor_attendanceusers {
    String name, type,timein,timeout;

    public Vendor_attendanceusers() {
    }

    public Vendor_attendanceusers(String name, String type, String timein, String timeout) {
        this.name = name;
        this.type = type;
        this.timein = timein;
        this.timeout = timeout;
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
