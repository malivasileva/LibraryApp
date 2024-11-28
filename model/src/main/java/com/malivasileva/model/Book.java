package com.malivasileva.model;

public class Book {
    private int id;
    private String title;
    private String authors;
    private String publisheAddress;
    private String publisherName;
    private int pages;
    private float price;
    private int copies;
    private int year;
    private String subject = "error";

    public Book (int id, String title, String authors, String publisheAddress, String publisherName, int pages, float price, int copies, int year) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisheAddress = publisheAddress;
        this.publisherName = publisherName;
        this.pages = pages;
        this.price = price;
        this.copies = copies;
        this.year = year;
    }

    public Book (int id, String title, String authors, String publisheAddress, String publisherName, int pages, float price, int copies, int year, String subject) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisheAddress = publisheAddress;
        this.publisherName = publisherName;
        this.pages = pages;
        this.price = price;
        this.copies = copies;
        this.year = year;
        this.subject = subject;
    }

    public Book (String title, String authors, String publisheAddress, String publisherName, int pages, float price, int copies, int year) {
        id = -1;
        this.title = title;
        this.authors = authors;
        this.publisheAddress = publisheAddress;
        this.publisherName = publisherName;
        this.pages = pages;
        this.price = price;
        this.copies = copies;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }

    public int getCopies() {
        return copies;
    }

    public float getPrice() {
        return price;
    }

    public String getPublisheAddress() {
        return publisheAddress;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getSubject() {
        return subject;
    }
}
