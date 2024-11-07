package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.model.Book;

import io.reactivex.rxjava3.core.Single;

public class UpdateBookUseCase {
    private final LibrBookRepository bookRepository;

    public UpdateBookUseCase(LibrBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<Boolean> execute(Book book) {
        return bookRepository.updateBook(book);
    }
}
