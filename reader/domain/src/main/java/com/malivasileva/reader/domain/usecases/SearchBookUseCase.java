package com.malivasileva.reader.domain.usecases;

import com.malivasileva.model.Book;
import com.malivasileva.reader.domain.repositories.ReaderBookRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchBookUseCase {
    ReaderBookRepository bookRepository;

    public SearchBookUseCase(ReaderBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<List<Book>> execute(String query) {
        return bookRepository.getBooksFor(query);
    }
}
