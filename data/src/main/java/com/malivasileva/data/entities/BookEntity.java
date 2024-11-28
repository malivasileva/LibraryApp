package com.malivasileva.data.entities;

public class BookEntity {
    private int id;
    private String title;
    private String authors;
    private String address;
    private String publisher;
    private int pages;
    private float price;
    private int copies;
    private int year;
    private String subject = "nothing";

    public BookEntity(int id, String title, String authors, String address, String publisher, int pages, float price, int copies, int year) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.address = address;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
        this.copies = copies;
        this.year = year;
    }

    public BookEntity(int id, String title, String authors, String address, String publisher, int pages, float price, int copies, int year, String subject) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.address = address;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
        this.copies = copies;
        this.year = year;
        this.subject = subject;
    }

    public float getPrice() {
        return price;
    }

    public int getCopies() {
        return copies;
    }

    public int getId() {
        return id;
    }

    public int getPages() {
        return pages;
    }

    public int getYear() {
        return year;
    }

    public String getAddress() {
        return address;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSubject() { return subject; }
}
