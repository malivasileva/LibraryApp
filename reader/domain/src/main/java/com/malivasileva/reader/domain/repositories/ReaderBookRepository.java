package com.malivasileva.reader.domain.repositories;

import com.malivasileva.model.Book;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ReaderBookRepository {
    Single<List<Book>> getBooksFor(String query);
    Single<Boolean> isBookAvailable(int bookNum);
}
