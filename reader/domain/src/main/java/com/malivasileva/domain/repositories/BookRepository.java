package com.malivasileva.domain.repositories;

import com.malivasileva.domain.model.Book;

import java.sql.SQLException;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface BookRepository {
    public List<Book> getAllBooks();
    public Single<List<Book>> getBooksFor(String query);
}
