package com.malivasileva.model;

import java.util.Date;

public class Lending {
    private final int id;
    private final int readerId;
    private final String readerName;
    private final int bookId;
    private final String title;
    private final String authors;
    private final Date lendDate;
    private final Date requiredDate;
    private final Date returnDate;

    public Lending(int id, int readerId, String readerName, int bookId,String title, String authors, Date lendDate, Date requiredDate, Date returnDate) {
        this.id = id;
        this.readerId = readerId;
        this.readerName = readerName;
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.lendDate = lendDate;
        this.requiredDate = requiredDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public int getReaderId() {
        return readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public int getBookId() {
        return bookId;
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
