package com.example.libraryapp.model;

public class Reader {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String password;

    Reader() {

    }
    public Reader (String name, String phone, String address, String password) {
        this.id = 0l;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
