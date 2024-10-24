package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.model.Lending;
import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetAllLendingsForBookUseCase {
    private final LibrLendingRepository lendingRepository;

    public GetAllLendingsForBookUseCase(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<List<Lending>> execute(int bookNum) {
        return lendingRepository.getLendingsForBookWith(bookNum);
    }

}
