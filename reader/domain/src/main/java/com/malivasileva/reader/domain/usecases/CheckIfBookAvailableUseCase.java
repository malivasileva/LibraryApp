package com.malivasileva.reader.domain.usecases;

import com.malivasileva.reader.domain.repositories.ReaderBookRepository;

import io.reactivex.rxjava3.core.Single;

public class CheckIfBookAvailableUseCase {
    private final ReaderBookRepository bookRepository;

    public CheckIfBookAvailableUseCase(
            ReaderBookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    public Single<Boolean> execute(int bookNum) {
        return bookRepository.isBookAvailable(bookNum);
    }
}
