package com.malivasileva.model;

public class Specialty {
    private int num;
    private String name;

    public Specialty(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }
}
