package com.malivasileva.data.entities;

public class StudyPlanEntity {
    private int num;
    private String subjectName;
    private String specialtyName;

    public StudyPlanEntity(int num, String subjectName, String specialtyName) {
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


