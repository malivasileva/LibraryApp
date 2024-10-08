package com.malivasileva.domain.repositories;

import com.malivasileva.domain.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    public List<Book> getAllBooks();
    public List<Book> getBooksFor(String query);
}
