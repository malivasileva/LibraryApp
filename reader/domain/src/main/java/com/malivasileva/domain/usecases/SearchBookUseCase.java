package com.malivasileva.domain.usecases;

import com.malivasileva.domain.model.Book;
import com.malivasileva.domain.repositories.BookRepository;

import java.util.List;

public class SearchBookUseCase {
    BookRepository bookRepository;

    public SearchBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> execute(String query) {
        return bookRepository.getBooksFor(query);
    }
}
