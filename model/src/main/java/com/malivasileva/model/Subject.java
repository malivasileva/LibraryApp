package com.malivasileva.model;

public class Subject {
    private int num;
    private String name;
    private String studySeriesName;

    public Subject(int num, String name, String studySeriesName) {
        this.num = num;
        this.name = name;
        this.studySeriesName = studySeriesName;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getStudySeriesName() {
        return studySeriesName;
    }
}
