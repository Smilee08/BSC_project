package com.example.final_pro;

public class MemberA {
    String name, member, room, emailaddress, contact;

    public MemberA() {
    }

    public MemberA(String name, String member, String room, String emailaddress, String contact) {
        this.name = name;
        this.member = member;
        this.room = room;
        this.emailaddress = emailaddress;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
