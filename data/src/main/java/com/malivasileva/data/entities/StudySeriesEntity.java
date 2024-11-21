package com.malivasileva.data.entities;

public class StudySeriesEntity {
    private int num;
    private String name;

    public StudySeriesEntity(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }
}
