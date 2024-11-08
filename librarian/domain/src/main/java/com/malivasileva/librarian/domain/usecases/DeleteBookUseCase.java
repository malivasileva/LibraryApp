package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrBookRepository;

import io.reactivex.rxjava3.core.Single;

public class DeleteBookUseCase {
    private final LibrBookRepository bookRepository;

    public DeleteBookUseCase(LibrBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<Boolean> execute (int id) {
        return bookRepository.deleteBook(id);
    }
}
