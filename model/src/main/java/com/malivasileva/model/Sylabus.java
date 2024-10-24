package com.malivasileva.model;

public class Sylabus {
    private int bookNum;
    private int studyPlanNum;

    public Sylabus(int bookNum, int studyPlanNum) {
        this.bookNum = bookNum;
        this.studyPlanNum = studyPlanNum;
    }

    public int getBookNum() {
        return bookNum;
    }

    public int getStudyPlanNum() {
        return studyPlanNum;
    }
}
