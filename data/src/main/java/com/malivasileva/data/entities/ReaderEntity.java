package com.malivasileva.data.entities;

public class ReaderEntity {
    private final long id;
    private final String name;
    private final String phone;
    private final String address;

    public ReaderEntity(long id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
