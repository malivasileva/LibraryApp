package com.malivasileva.data.entities;

public class SylabusEntity {
    private String subject;
    private int bookNum;
    private String title;
    private String authors;
    private int studyPlanNum;

    public SylabusEntity(String subject, int bookNum, String title, String authors, int studyPlanNum) {
        this.subject = subject;
        this.bookNum = bookNum;
        this.title = title;
        this.authors = authors;
        this.studyPlanNum = studyPlanNum;
    }

    public int getBookNum() {
        return bookNum;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getStudyPlanNum() {
        return studyPlanNum;
    }
}
