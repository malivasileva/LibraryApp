package com.malivasileva.model;

import java.util.Date;

public class Lending {
    private final int id;
    private final String title;
    private final String authors;
    private final Date lendDate;
    private final Date requiredDate;
    private final Date returnDate;

    public Lending(int id, String title, String authors, Date lendDate, Date requiredDate, Date returnDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.lendDate = lendDate;
        this.requiredDate = requiredDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
