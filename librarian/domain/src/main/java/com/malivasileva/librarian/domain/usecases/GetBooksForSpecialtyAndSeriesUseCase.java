package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrBookRepository;
import com.malivasileva.model.Book;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetBooksForSpecialtyAndSeriesUseCase {
    private final LibrBookRepository bookRepository;

    public GetBooksForSpecialtyAndSeriesUseCase(
            LibrBookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    public Single<List<Book>> execute(int specialty, int series) {
        return bookRepository.getBooksForSpecialtyAndSeries(specialty, series);
    }
}
