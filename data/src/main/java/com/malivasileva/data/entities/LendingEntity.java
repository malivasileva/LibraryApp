package com.malivasileva.data.entities;

import java.util.Date;

public class LendingEntity {
    private final int id;
    private final int readerId;
    private final String readerName;
    private final int bookId;
    private final String title;
    private final String authors;
    private final Date startDate;
    private final Date requiredDate;
    private final Date returnedDate;

    public LendingEntity(int id, int readerId, String readerName, int bookId, String title, String authors, Date startDate, Date requiredDate, Date returnedDate) {
        this.id = id;
        this.readerId = readerId;
        this.readerName = readerName;
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.startDate = startDate;
        this.requiredDate = requiredDate;
        this.returnedDate = returnedDate;
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
