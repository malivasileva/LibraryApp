package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.model.Lending;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UpdateLendingUseCase {
    private final LibrLendingRepository lendingRepository;

    public UpdateLendingUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<Boolean> execute (Lending lending) {
        return lendingRepository.updateLending(lending);
    }
}
