package com.malivasileva.data.entities;

import java.util.Date;

public class LendingEntity {
    private int id;
    private String title;
    private String authors;
    private Date startDate;
    private Date requiredDate;
    private Date returnedDate;

    public LendingEntity(int id, String title, String authors, Date startDate, Date requiredDate, Date returnedDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.startDate = startDate;
        this.requiredDate = requiredDate;
        this.returnedDate = returnedDate;
    }

    public int getId() {
        return id;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }
}
