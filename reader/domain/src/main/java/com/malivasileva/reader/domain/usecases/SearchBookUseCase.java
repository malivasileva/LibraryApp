package com.malivasileva.reader.domain.usecases;

import com.malivasileva.model.Book;
import com.malivasileva.reader.domain.repositories.BookRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchBookUseCase {
    BookRepository bookRepository;

    public SearchBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<List<Book>> execute(String query) {
        return bookRepository.getBooksFor(query);
    }
}
