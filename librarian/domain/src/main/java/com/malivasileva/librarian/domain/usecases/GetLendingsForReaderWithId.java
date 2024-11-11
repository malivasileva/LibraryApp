package com.malivasileva.librarian.domain.usecases;

import com.malivasileva.librarian.domain.repositories.LibrLendingRepository;
import com.malivasileva.model.Lending;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetLendingsForReaderWithId {
    private final LibrLendingRepository lendingRepository;

    public GetLendingsForReaderWithId(LibrLendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Single<List<Lending>> execute(int num) {
        return lendingRepository.getLendingsForReaderWithId(num);
    }
}
