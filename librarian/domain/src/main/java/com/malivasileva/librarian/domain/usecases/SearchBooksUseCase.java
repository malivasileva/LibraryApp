package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.model.Book;
import com.malivasileva.librarian.domain.repositories.LibrBookRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class SearchBooksUseCase {
    private final LibrBookRepository bookRepository;

    public SearchBooksUseCase(LibrBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<List<Book>> execute(String query) {
        return bookRepository.getBooksFor(query);
    }
}
