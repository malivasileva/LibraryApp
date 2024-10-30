package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;

import java.util.Date;

import io.reactivex.rxjava3.core.Single;

public class ExpandReturnDateUseCase {
    private final LibrLendingRepository lendingRepository;

    public ExpandReturnDateUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<Boolean> execute(int lendingId) {

        return lendingRepository.expandReturnDate(lendingId);
    }
}
