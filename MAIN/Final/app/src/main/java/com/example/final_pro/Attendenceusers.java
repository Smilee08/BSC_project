package com.example.final_pro;

public class Attendenceusers {
    String name,timein, timeout, date;

    public Attendenceusers() {
    }

    public Attendenceusers(String name, String timein, String timeout,String date) {
        this.name = name;
        this.timein = timein;
        this.timeout = timeout;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
