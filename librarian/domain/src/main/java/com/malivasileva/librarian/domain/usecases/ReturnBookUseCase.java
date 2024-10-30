package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;

import java.sql.Date;
import java.util.Calendar;

import io.reactivex.rxjava3.core.Single;

public class ReturnBookUseCase {

    private final LibrLendingRepository lendingRepository;

    public ReturnBookUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<Boolean> execute(int lendingId) {
        return lendingRepository.returnBook(lendingId);
    }
}
