package com.malivasileva.librarian.domain.repositories;

import com.malivasileva.model.Book;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LibrBookRepository {
    Single<List<Book>> getBooksFor(String query);
    Single<Book> getBookWithId(int bookId);
    Single<Boolean> updateBook(Book book);
    Single<Boolean> deleteBook(int id);
    Single<Boolean> addBook(Book book);
    Single<List<Book>> getBooksForSpecialtyAndSeries(int specialty, int series);

}
