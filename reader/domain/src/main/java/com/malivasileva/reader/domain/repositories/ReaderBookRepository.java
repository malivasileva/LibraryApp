package com.malivasileva.reader.domain.repositories;

import com.malivasileva.model.Book;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ReaderBookRepository {
    public Single<List<Book>> getBooksFor(String query);
}