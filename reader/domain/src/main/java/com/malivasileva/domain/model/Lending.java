package com.malivasileva.domain.model;

import java.util.Date;

public class Lending {
    private int id;
    private int book;
    private int reader;
    private Date lendDate;
    private Date requiredDate;
    private Date returnDate;

    public Lending(int id, int book, int reader, Date lendDate, Date requiredDate, Date returnDate) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.lendDate = lendDate;
        this.requiredDate = requiredDate;
    }
}
