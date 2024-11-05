package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.model.Book;

import io.reactivex.rxjava3.core.Single;

public class GetBookWithIdUseCase {
    private final LibrBookRepository bookRepository;

    public GetBookWithIdUseCase(LibrBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Single<Book> execute(int bookId) {
        return bookRepository.getBookWithId(bookId);
    }
}
