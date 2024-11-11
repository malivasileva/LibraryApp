package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.model.Lending;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetLendingsForBookWithIdUseCase {
    private final LibrLendingRepository lendingRepository;

    public GetLendingsForBookWithIdUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<List<Lending>> execute(int num) {
        return lendingRepository.getLendingsForBookWithId(num);
    }
}
