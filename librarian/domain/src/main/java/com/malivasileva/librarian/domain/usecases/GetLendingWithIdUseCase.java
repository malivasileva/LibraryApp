package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.model.Lending;

import io.reactivex.rxjava3.core.Single;

public class GetLendingWithIdUseCase {
    private final LibrLendingRepository lendingRepository;

    public GetLendingWithIdUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<Lending> execute(int lendingId) {
        return lendingRepository.getLendingWithId(lendingId);
    }
}
