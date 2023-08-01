package com.example.final_pro;

public class Register {

    String Fullname, Roomno, MobileNumber;

    public Register() {
    }

    public Register(String fullname, String roomno, String mobileNumber) {
        Fullname = fullname;
        Roomno = roomno;
        MobileNumber = mobileNumber;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getRoomno() {
        return Roomno;
    }

    public void setRoomno(String roomno) {
        Roomno = roomno;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
}
