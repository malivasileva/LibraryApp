package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.model.Lending;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetAllCurrentLendingsUseCase {
    private final LibrLendingRepository lendingRepository;

    public GetAllCurrentLendingsUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<List<Lending>> execute() {
        return lendingRepository.getAllCurrentLendings();
    }
}