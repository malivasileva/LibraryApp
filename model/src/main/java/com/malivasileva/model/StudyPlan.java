package com.malivasileva.model;

public class StudyPlan {
    private int num;
    private String subjectName;
    private String specialtyName;

    public StudyPlan(int num, String subjectName, String specialtyName) {
        this.num = num;
        this.specialtyName = specialtyName;
        this.subjectName = subjectName;
    }

    public int getNum() {
        return num;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
