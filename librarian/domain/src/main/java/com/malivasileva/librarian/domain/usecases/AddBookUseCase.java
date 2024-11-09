package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.model.Book;

import io.reactivex.rxjava3.core.Single;

public class AddBookUseCase {
    private final LibrBookRepository bookRepository;

    public AddBookUseCase(LibrBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<Boolean> execute(Book book) {
        return bookRepository.addBook(book);
    }
}
