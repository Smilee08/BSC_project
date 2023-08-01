package com.example.final_pro;

public class Staffusers {
    String name, work, department,availability,contactno;

    public Staffusers() {
    }

    public Staffusers(String name, String work, String department, String availability,String contactno) {
        this.name = name;
        this.work = work;
        this.department = department;
        this.availability = availability;
        this.contactno = contactno;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
