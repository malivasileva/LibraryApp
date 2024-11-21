package com.malivasileva.model;

public class Subject {
    private int num;
    private String name;

    public Subject(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
