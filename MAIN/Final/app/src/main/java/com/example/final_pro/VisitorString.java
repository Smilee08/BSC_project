package com.example.final_pro;

public class VisitorString {
    String name, contact, date, time, guest_of, roomno;

    public VisitorString() {
    }

    public VisitorString(String name, String contact, String date, String time, String guest_of, String roomno) {
        this.name = name;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.guest_of = guest_of;
        this.roomno = roomno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getGuest_of() {
        return guest_of;
    }

    public void setGuest_of(String guest_of) {
        this.guest_of = guest_of;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }
}
