package com.malivasileva.data.entities;

import java.util.Date;

public class LendingEntity {
    private int id;
    private int bookId;
    private int readerId;
    private Date startDate;
    private Date requiredDate;
    private Date returnedDate;

    public LendingEntity(int id, int bookId, int readerId, Date startDate, Date requiredDate, Date returnedDate) {
        this.id = id;
        this.bookId = bookId;
        this.readerId = readerId;
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

    public int getBookId() {
        return bookId;
    }

    public int getReaderId() {
        return readerId;
    }
}
