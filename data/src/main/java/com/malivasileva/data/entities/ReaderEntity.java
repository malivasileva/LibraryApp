package com.malivasileva.data.entities;

public class ReaderEntity {
    private int id;
    private String name;
    private String phone;
    private String address;

    public ReaderEntity(int id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
