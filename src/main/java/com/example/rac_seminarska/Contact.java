package com.example.rac_seminarska;


import java.io.Serializable;

public class Contact extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        super(name);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}