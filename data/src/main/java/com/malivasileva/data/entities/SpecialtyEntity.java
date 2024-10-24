package com.malivasileva.data.entities;

public class SpecialtyEntity {
    private int num;
    private String name;

    public SpecialtyEntity(int num, String name) {
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
