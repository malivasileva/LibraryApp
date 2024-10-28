package com.malivasileva.model;

public class Reader {
    private long card;
    private String name;
    private String phone;
    private String address;

    public Reader(long card, String name, String phone, String address) {
        this.card = card;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public long getCard() {
        return card;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
